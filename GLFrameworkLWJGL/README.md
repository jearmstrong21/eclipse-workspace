# GLFrameworkLWJGL



NOTES and COMMON MISTAKES


If the texture is blue:
- The framebuffer probably has no color attachment.
- The color texture is probably undefined memory.
- Part of the color texture is undefined memory (like only a quarter of the texture is written to).
- OpenGL initializes it to blue because of one or both of the above.


If stuff rendered to a framebuffer doesn't have depth:
- Attach a RBO to the depth attachment