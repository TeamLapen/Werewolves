import React from "react";

export const baseUrl = 'https://raw.githubusercontent.com/TeamLapen/Werewolves/'
export const baseCommit = 'f3c8ff0f7e4464f768adbac5b761e210729c77d1'
export const effectPath = '/src/main/resources/assets/werewolves/textures/mob_effect/'

export const EffectImg = ({src, alt}) => (
    <img src={src} alt={alt} style={{height: 70, imageRendering: "pixelated"}}/>
);

export const Effect = ({children, title, icon, commit=baseCommit}) => (
    <div style={{ marginBottom: '15px'}}>
        <div className="container" style={{ display: 'flex', alignItems: 'center', marginLeft: '-20px'}}>
            <div className="image">
                <EffectImg src={baseUrl.concat(commit).concat(effectPath).concat(icon)} alt={title}/>
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