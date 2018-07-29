#version 410 core

in vec2 uv;
in vec3 normal;

layout (location=0) out vec4 fragColor;

subroutine vec3 shadingRoutine();
subroutine uniform shadingRoutine shadingType;

subroutine(shadingRoutine)
vec3 shadeNormals(){
    return normal*0.5+0.5;
}

subroutine(shadingRoutine)
vec3 shadeUV(){
    return vec3(uv,0.0);
}

void main(){
    fragColor=vec4(shadingType(),1.0);
}
