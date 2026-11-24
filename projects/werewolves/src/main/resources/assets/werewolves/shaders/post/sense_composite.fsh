#version 330

// world color
uniform sampler2D MainSampler;
// heavily blurred silhouettes of the sensed entities (rgb = health color * intensity * coverage, a = coverage)
uniform sampler2D FogSampler;
// 1x1 parameters: r = vision progress
uniform sampler2D ParamsSampler;

layout(std140) uniform SamplerInfo {
    vec2 OutSize;
    vec2 InSize;
};

in vec2 texCoord;

out vec4 fragColor;

void main() {
    vec3 world = texture(MainSampler, texCoord).rgb;
    float progress = texture(ParamsSampler, vec2(0.5)).r;

    // dim and desaturate the world, darker towards the screen edges
    float luma = dot(world, vec3(0.2126, 0.7152, 0.0722));
    vec2 centered = (texCoord - 0.5) * vec2(OutSize.x / OutSize.y, 1.0);
    float vignette = smoothstep(0.25, 0.95, length(centered));
    vec3 dimmed = mix(vec3(luma), world, 0.4) * mix(0.7, 0.3, vignette);
    vec3 color = mix(world, dimmed, progress);

    vec4 fog = texture(FogSampler, texCoord);
    if (fog.a > 0.0) {
        vec3 fogColor = fog.rgb / fog.a;
        float density = smoothstep(0.0, 1.0, fog.a) * 0.4;
        color = mix(color, fogColor, density * progress);
    }

    fragColor = vec4(color, 1.0);
}
