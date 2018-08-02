#version 410 core

layout (location=0) in vec2 inPos;

uniform vec2 offset;
uniform float gridSize;

float lerp(float t,float a,float b){
    return t*(b-a)+a;
}

float norm(float t,float a,float b){
    return (t-b)/(b-a);
}

float map(float t,float s1,float e1,float s2,float e2){
    return lerp(norm(t,s1,e1),s2,e2);
}

void main(){
    float x=inPos.x+offset.x;
    float y=inPos.y+offset.y;
    x/=gridSize;
    y/=gridSize;
    x*=2.0;
    y*=2.0;
    x-=1.0;
    y-=1.0;
    gl_Position=vec4(x,y,0.0,1.0);
}

