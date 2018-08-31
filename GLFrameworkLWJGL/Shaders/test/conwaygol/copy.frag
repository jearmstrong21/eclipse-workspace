#version 410 core

in vec2 uv;

uniform sampler2D fromTex;

layout (location=0) out vec4 fragColor;

void main(){
    fragColor=texture(fromTex,uv);
}
