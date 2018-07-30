#version 410 core

in vec2 uv;

out vec4 fragColor;

uniform sampler2D tex;
uniform float texDX;
uniform float texDY;

float luma(vec3 color){
    return 0.2126*color.x+0.7152*color.y+0.0722*color.z;//OpenGL 4.0 Shading Language Cookbook, page 154
}

subroutine vec3 kernel(vec3 _00,vec3 _01,vec3 _02,vec3 _10,vec3 _11,vec3 _12,vec3 _20,vec3 _21,vec3 _22);
subroutine uniform kernel kernelType;

subroutine(kernel)
vec3 laplacian(vec3 _00,vec3 _01,vec3 _02,vec3 _10,vec3 _11,vec3 _12,vec3 _20,vec3 _21,vec3 _22){
    return -_01-_10-_21-_12+4.0*_11;
}
subroutine(kernel)
vec3 laplacianLuma(vec3 _00,vec3 _01,vec3 _02,vec3 _10,vec3 _11,vec3 _12,vec3 _20,vec3 _21,vec3 _22){
    return vec3(-luma(_01)-luma(_10)-luma(_21)-luma(_12)+4.0*luma(_11));
}
subroutine(kernel)
vec3 laplacianCorners(vec3 _00,vec3 _01,vec3 _02,vec3 _10,vec3 _11,vec3 _12,vec3 _20,vec3 _21,vec3 _22){
    return -_00-_01-_02-_10+8.0*_11-_12-_20-_21-_22;
}
subroutine(kernel)
vec3 laplacianCornersLuma(vec3 _00,vec3 _01,vec3 _02,vec3 _10,vec3 _11,vec3 _12,vec3 _20,vec3 _21,vec3 _22){
    return vec3(-luma(_00)-luma(_01)-luma(_02)-luma(_10)+8.0*luma(_11)-luma(_12)-luma(_20)-luma(_21)-luma(_22));
}
subroutine(kernel)
vec3 noKernel(vec3 _00,vec3 _01,vec3 _02,vec3 _10,vec3 _11,vec3 _12,vec3 _20,vec3 _21,vec3 _22){
    return _11;
}
subroutine(kernel)
vec3 edgeDetection(vec3 _00,vec3 _01,vec3 _02,vec3 _10,vec3 _11,vec3 _12,vec3 _20,vec3 _21,vec3 _22){
    return vec3(luma(_00)+luma(_02)+luma(_20)+luma(_22)+2.0*luma(_11));
}
subroutine(kernel)
vec3 sobelLuma(vec3 _00,vec3 _01,vec3 _02,vec3 _10,vec3 _11,vec3 _12,vec3 _20,vec3 _21,vec3 _22){
    float Gx=luma(_00)+2.0*luma(_01)+luma(_02)-luma(_20)-2.0*luma(_21)-luma(_22);
    float Gy=luma(_00)+2.0*luma(_10)+luma(_20)-luma(_02)-2.0*luma(_12)-luma(_22);
    return vec3(sqrt(Gx*Gx+Gy*Gy));
}
subroutine(kernel)
vec3 sobelVector(vec3 _00,vec3 _01,vec3 _02,vec3 _10,vec3 _11,vec3 _12,vec3 _20,vec3 _21,vec3 _22){
    vec3 Gx=_00+2.0*_01+_02-_20-2.0*_21-_22;
    vec3 Gy=_00+2.0*_10+_20-_02-2.0*_12-_22;
    return sqrt(Gx*Gx+Gy*Gy);
}
subroutine(kernel)
vec3 sharpen(vec3 _00,vec3 _01,vec3 _02,vec3 _10,vec3 _11,vec3 _12,vec3 _20,vec3 _21,vec3 _22){
    return -_01-_21-_10-_12+5.0*_11;
}

void main(){
    vec3 _00=texture(tex,uv+vec2(-texDX,-texDY)).xyz;
    vec3 _01=texture(tex,uv+vec2(-texDX,0.0)).xyz;
    vec3 _02=texture(tex,uv+vec2(-texDX, texDY)).xyz;
    vec3 _10=texture(tex,uv+vec2(0.0,-texDY)).xyz;
    vec3 _11=texture(tex,uv+vec2(0.0,0.0)).xyz;
    vec3 _12=texture(tex,uv+vec2(0.0, texDY)).xyz;
    vec3 _20=texture(tex,uv+vec2( texDX,-texDY)).xyz;
    vec3 _21=texture(tex,uv+vec2( texDX,0.0)).xyz;
    vec3 _22=texture(tex,uv+vec2( texDX, texDY)).xyz;
    vec3 col=kernelType(_00,_01,_02,_10,_11,_12,_20,_21,_22);
    fragColor=vec4(col,1.0);
//    fragColor=vec4(uv,0.0,1.0);
}
