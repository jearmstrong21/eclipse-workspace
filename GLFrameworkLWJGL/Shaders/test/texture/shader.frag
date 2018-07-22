#version 410 core

in vec2 uv;

uniform sampler2D tex1;
uniform sampler2D tex2;

out vec4 fragColor;

void main(){
    fragColor=texture(tex1,uv)*0.5+texture(tex2,uv)*0.5;
}
