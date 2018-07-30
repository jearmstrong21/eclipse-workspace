#version 410 core

flat in vec2 pos;

layout (location=0) out vec4 fragColor;

#define PI 3.1415926536

vec3 hsv2rgb(vec3 c) {
    vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
    vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);
    return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);
}


void main(){
    float ang=atan(pos.y,pos.x)/PI+0.5;
    fragColor=vec4(hsv2rgb(vec3(ang,0.5,1.0)),1.0);
}
