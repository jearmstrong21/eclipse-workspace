#version 410 core

in vec2 uv;

uniform sampler2D tex1;
uniform sampler2D tex2;

uniform float w;
uniform float h;

layout (location=0) out vec4 fragColor;

void main(){
    fragColor=texture(tex1,uv);
}
