import React from "react";

export const baseUrl = 'https://raw.githubusercontent.com/TeamLapen/Werewolves/'
export const baseCommit = 'f3c8ff0f7e4464f768adbac5b761e210729c77d1'
export const skillPath = '/src/main/resources/assets/werewolves/textures/skills/'
export const actionPath = '/src/main/resources/assets/werewolves/textures/actions/'


const SkillImg = ({src, alt}) => (
    <img src={src} alt={alt} style={{height: 70, imageRendering: "pixelated"}}/>
);

const UnlockAction = ({children}) => (
    <span style={{
        color: '#0e9c2f',
        fontStyle: 'italic'
    }}>
        {children}
    </span>
);

const UpgradesSkill = ({children}) => (
    <span style={{
        color: '#10a8b3',
        fontStyle: 'italic'
    }}>
        {children}
    </span>
);

const OnlyEffects = ({children}) => (
    <span style={{
        color: '#bd8e31',
        fontStyle: 'italic'
    }}>
        {children}
    </span>
);

const SkillOrAction = ({children, title, iconPath}) => (
    <div style={{ marginBottom: '15px'}}>
        <div className="container" style={{ display: 'flex', alignItems: 'center', marginLeft: '-20px'}}>
            <div className="image">
                <SkillImg src={iconPath} alt={title}/>
            </div>
            <div className="text" style={{ marginLeft: '10px'}}>
                <h2>{title}</h2>
            </div>
        </div>
        <span >
            {children}
        </span>
    </div>
);

const Skill = ({children, title, icon, commit=baseCommit})=> (
    <SkillOrAction title={title} iconPath={baseUrl.concat(commit).concat(skillPath).concat(icon)}>
        {children}
    </SkillOrAction>
);

const Action = ({children, title, icon, commit=baseCommit})=> (
    <SkillOrAction title={title} iconPath={baseUrl.concat(commit).concat(actionPath).concat(icon)}>
        {children}
    </SkillOrAction>
);

const SkillBranch = ({children, title}) =>(
    <div>
        <h1>{title}</h1>
        <div style={{ marginLeft: '40px'}}>
            {children}
        </div>
    </div>
);

const skillImage = (path, commit=baseCommit) => {
    return baseUrl.concat(commit).concat(skillPath).concat(path);
}

const actionImage = (path, commit=baseCommit) => {
    return baseUrl.concat(commit).concat(actionPath).concat(path);
}