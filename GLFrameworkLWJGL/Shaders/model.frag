#version 410 core

out vec4 fragColor;

uniform bool shadeNormals;
uniform bool shadeUVs;

in vec3 pos;
in vec3 normal;
in vec2 uv;

void main(){
    fragColor=vec4(0.0,0.0,0.0,1.0);
    if(shadeNormals)fragColor=vec4(normal*0.5+0.5,1.0);
    if(shadeUVs)fragColor=vec4(uv,0.0,1.0);
}
