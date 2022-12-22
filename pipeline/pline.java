import java.awt.image.BufferedImage;
import java.util.List;

public class Renderer {

    // The width and height of the output image
    private int width;
    private int height;

    // The projection matrix to use for rendering
    private Matrix4 projection;

    // The view matrix to use for rendering
    private Matrix4 view;

    // The list of meshes to render
    private List<Mesh> meshes;

    // The image to render to
    private BufferedImage image;

    public Renderer(int width, int height, Matrix4 projection, Matrix4 view, List<Mesh> meshes) {
        this.width = width;
        this.height = height;
        this.projection = projection;
        this.view = view;
        this.meshes = meshes;
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }

    public void render() {
        // Clear the image
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                image.setRGB(x, y, 0x00000000);
            }
        }

        // Render each mesh
        for (Mesh mesh : meshes) {
            // Transform the mesh vertices into screen space using the projection and view
            // matrices
            Vector3[] vertices = mesh.getVertices();
            for (int i = 0; i < vertices.length; i++) {
                vertices[i] = projection.mul(view.mul(vertices[i])).perspectiveDivide();
            }

            // Loop through each triangle in the mesh and rasterize it to the image
            int[] indices = mesh.getIndices();
            for (int i = 0; i < indices.length; i += 3) {
                // Get the vertices of the triangle
                Vector3 v0 = vertices[indices[i]];
                Vector3 v1 = vertices[indices[i + 1]];
                Vector3 v2 = vertices[indices[i + 2]];

                // Sort the vertices by y-coordinate
                if (v1.getY() < v0.getY()) {
                    Vector3 temp = v0;
                    v0 = v1;
                    v1 = temp;
                }
                if (v2.getY() < v0.getY()) {
                    Vector3 temp = v0;
                    v0 = v2;
                    v2 = temp;
                }
                if (v2.getY() < v1.getY()) {
                    Vector3 temp = v1;
                    v1 = v2;
                    v2 = temp;
                }

                // Compute the slopes of the triangle sides
                float slope1 = (v1.getX() - v0.getX()) / (v1.getY() - v0.getY());
                float slope2 = (v2.getX() - v0.getX()) / (v2.getY() - v0.getY());
                float slope3 = (v3.getX() - v0.getX()) / (v3.getY() - v0.getY());
            }
        }

    }

}
