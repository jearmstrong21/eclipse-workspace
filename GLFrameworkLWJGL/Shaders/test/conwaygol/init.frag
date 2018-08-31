#version 410 core

in vec2 uv;

layout (location=0) out vec4 fragColor;

float random (vec2 st) {
    return fract(sin(dot(st.xy,
                         vec2(12.9898,78.233)))*
                 43758.5453123);
}

void main(){
    fragColor=vec4( random(uv) < 0.8 ? 0.0 : 1.0 );
}
