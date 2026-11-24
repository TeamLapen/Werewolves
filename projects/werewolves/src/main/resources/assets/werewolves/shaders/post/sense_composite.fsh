#version 330

#moj_import <minecraft:globals.glsl>

// world color and depth
uniform sampler2D MainSampler;
uniform sampler2D MainDepthSampler;
// flat colored silhouettes of the sensed entities (rgb = health color * intensity, a = coverage) and their depth
uniform sampler2D SenseSampler;
uniform sampler2D SenseDepthSampler;
// blurred silhouettes
uniform sampler2D GlowSampler;
// 1x1 parameters: r = vision progress
uniform sampler2D ParamsSampler;

layout(std140) uniform SamplerInfo {
    vec2 OutSize;
    vec2 InSize;
};

in vec2 texCoord;

out vec4 fragColor;

const vec2 DIRECTIONS[8] = vec2[](
    vec2(1.0, 0.0), vec2(-1.0, 0.0), vec2(0.0, 1.0), vec2(0.0, -1.0),
    vec2(0.7071, 0.7071), vec2(-0.7071, 0.7071), vec2(0.7071, -0.7071), vec2(-0.7071, -0.7071)
);

void main() {
    vec3 world = texture(MainSampler, texCoord).rgb;
    float progress = texture(ParamsSampler, vec2(0.5)).r;

    // dim and desaturate the world, darker towards the screen edges
    float luma = dot(world, vec3(0.2126, 0.7152, 0.0722));
    vec2 centered = (texCoord - 0.5) * vec2(OutSize.x / OutSize.y, 1.0);
    float vignette = smoothstep(0.25, 0.95, length(centered));
    vec3 dimmed = mix(vec3(luma), world, 0.4) * mix(0.7, 0.3, vignette);
    vec3 color = mix(world, dimmed, progress);

    vec4 sense = texture(SenseSampler, texCoord);
    vec4 glow = texture(GlowSampler, texCoord);

    // soft glow around the silhouettes
    color += glow.rgb * (1.0 - sense.a) * 0.9 * progress;

    if (sense.a > 0.0) {
        // rim: silhouette pixels next to uncovered pixels
        vec2 rimStep = max(1.5, OutSize.y / 540.0) / OutSize;
        float minNeighbor = 1.0;
        for (int i = 0; i < 8; i++) {
            minNeighbor = min(minNeighbor, texture(SenseSampler, texCoord + DIRECTIONS[i] * rimStep).a);
        }
        float rim = 1.0 - minNeighbor;

        // hidden behind blocks? Compare the distance ratio (1 - depth is roughly proportional to 1 / distance),
        // so the threshold does not depend on the depth precision at that distance
        float senseDepth = texture(SenseDepthSampler, texCoord).r;
        float worldDepth = texture(MainDepthSampler, texCoord).r;
        float hidden = step(1.03, (1.0 - worldDepth) / max(1.0 - senseDepth, 1e-6));

        // slow heartbeat pulse
        float seconds = GameTime * 1200.0;
        float beat = pow(max(sin(seconds * 6.2831853 * 1.1), 0.0), 8.0);

        // the blurred coverage is lower near the edge, so the fill gets stronger towards the outline
        float edge = 1.0 - smoothstep(0.55, 1.0, glow.a);
        float fill = mix(0.12, 0.35, hidden) + edge * mix(0.15, 0.3, hidden) + beat * 0.12;

        color = mix(color, sense.rgb, clamp(fill, 0.0, 1.0) * progress);
        color += sense.rgb * rim * 0.9 * progress;
    }

    fragColor = vec4(color, 1.0);
}
