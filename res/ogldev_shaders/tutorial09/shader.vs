#version 330

layout (location = 0) in vec3 Position;

uniform mat4 world;

out vec4 Color;

void main()
{
    gl_Position = world * vec4(Position, 1.0);
    Color = vec4(clamp(Position, 0.0, 1.0), 1.0);
}
