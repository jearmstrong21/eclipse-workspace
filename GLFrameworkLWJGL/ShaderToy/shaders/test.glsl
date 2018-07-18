#version 410 core

in vec2 fragCoord;

out vec4 fragColor;

uniform float iTime;
uniform vec2 iResolution;

#include MATH


void main(){
    float offsetX=0.0;
    float offsetY=0.0;
    if(iResolution.x>iResolution.y){
        offsetX=0.5*(iResolution.x-iResolution.y);
        offsetY=0.0;
    }else{
        offsetX=0.0;
        offsetY=0.5*(iResolution.y-iResolution.x);
    }
    float x=map(fragCoord.x,offsetX,iResolution.x-offsetX,0.0,1.0);
    float y=map(fragCoord.y,offsetY,iResolution.y-offsetY,0.0,1.0);
    fragColor=vec4(x,y,0.0,1.0);
    if(x<0.0||y<0.0||x>1.0||y>1.0)fragColor.z=1.0;
}
