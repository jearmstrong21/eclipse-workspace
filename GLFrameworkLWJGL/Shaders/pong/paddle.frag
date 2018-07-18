#version 410 core

uniform float r;
uniform float g;
uniform float b;

out vec4 fragColor;

void main(){
    fragColor=vec4(r,g,b,1.0);
}
