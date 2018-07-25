#version 410 core

in vec2 uv;

uniform sampler2D tex1;
uniform sampler2D tex2;

uniform int frames;

uniform float w;
uniform float h;

layout (location=0) out vec4 fragColor1;
layout (location=1) out vec4 fragColor2;

void main(){
    vec4 col=texture(frames%2==0?tex1:tex2,uv);
    col.x+=0.1;
    if(frames%2==0)fragColor2=col;
    else fragColor1=col;
}
