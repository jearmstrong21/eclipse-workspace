#version 410 core

flat in vec2 pos;

out vec4 fragColor;

#define PI 3.1415926536

void main(){
    float ang=atan(pos.y/pos.x)/PI+0.5;
    fragColor=vec4(vec3(ang),1.0);
}
