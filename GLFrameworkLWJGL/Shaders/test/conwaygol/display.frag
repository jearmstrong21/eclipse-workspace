#version 410 core

in vec2 uv;

uniform sampler2D inTex;

uniform float dx;
uniform float dy;

layout (location=0) out vec4 fragColor;

vec4 tex(vec2 uv){
    while(uv.x<0.0)uv.x+=1.0;
    while(uv.y<0.0)uv.y+=1.0;
    while(uv.x>1.0)uv.x-=1.0;
    while(uv.y>1.0)uv.y-=1.0;
    return texture(inTex,uv);
}

vec3 hsv2rgb(vec3 c) {
    vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
    vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);
    return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);
}

vec3 hsv2rgb(float h,float s,float v){
    return hsv2rgb(vec3(h,s,v));
}

void main(){
//    float num=0.0;
//    num+=tex( uv+vec2(-dx,-dy) ).x;
//    num+=tex( uv+vec2(-dx,0.0) ).x;
//    num+=tex( uv+vec2(-dx, dy) ).x;
//    num+=tex( uv+vec2(0.0,-dy) ).x;
    //    num+=tex( uv+vec2(0.0,0.0) ).x;
//    num+=tex( uv+vec2(0.0, dy) ).x;
//    num+=tex( uv+vec2( dx,-dy) ).x;
//    num+=tex( uv+vec2( dx,0.0) ).x;
//    num+=tex( uv+vec2( dx, dy) ).x;
    fragColor=texture(inTex,uv);
//    fragColor=vec4(hsv2rgb(num/8.0,0.5,1.0),1.0);
//    fragColor=vec4(num/8.0);
}
