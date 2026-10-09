public class week_7_Assaignment_problems_1 {

    static abstract class Shape {
        private static int counter = 0;
        private final String shapeId;

        protected Shape() {
            shapeId = "SH-" + (++counter);
        }

        public abstract double calculateArea();

        public abstract void scale(double factor);

        public abstract void scale(double xFactor, double yFactor);

        public String getShapeId() {
            return shapeId;
        }

        public static void printArea(Shape s) {
            System.out.printf("Area of %s: %.2f%n", s.getShapeId(), s.calculateArea());
        }
    }

    static class CircleShape extends Shape {
        private double radius;

        public CircleShape(double radius) {
            if (radius <= 0) {
                throw new IllegalArgumentException("Radius must be positive.");
            }
            this.radius = radius;
        }

        @Override
        public double calculateArea() {
            return Math.PI * radius * radius;
        }

        @Override
        public void scale(double factor) {
            if (factor <= 0) throw new IllegalArgumentException("Scale factor must be positive.");
            radius *= factor;
        }

        @Override
        public void scale(double xFactor, double yFactor) {
            if (xFactor <= 0 || yFactor <= 0) {
                throw new IllegalArgumentException("Scale factors must be positive.");
            }
            // A circle cannot remain a circle under unequal scaling, so use the mean factor.
            radius *= (xFactor + yFactor) / 2.0;
        }
    }

    static class SquareShape extends Shape {
        private double side;

        public SquareShape(double side) {
            if (side <= 0) {
                throw new IllegalArgumentException("Side must be positive.");
            }
            this.side = side;
        }

        @Override
        public double calculateArea() {
            return side * side;
        }

        @Override
        public void scale(double factor) {
            if (factor <= 0) throw new IllegalArgumentException("Scale factor must be positive.");
            side *= factor;
        }

        @Override
        public void scale(double xFactor, double yFactor) {
            if (xFactor <= 0 || yFactor <= 0) {
                throw new IllegalArgumentException("Scale factors must be positive.");
            }
            // Unequal scaling makes a rectangle; this class represents squares only.
            // Keep the shape a square by using the mean of the two supplied factors.
            side *= (xFactor + yFactor) / 2.0;
        }
    }

    public static void main(String[] args) {
        CircleShape c = new CircleShape(5.0);
        SquareShape sq = new SquareShape(4.0);

        System.out.printf("Circle area: %.2f%n", c.calculateArea());
        System.out.printf("Square area: %.2f%n", sq.calculateArea());

        sq.scale(2.0);
        System.out.printf("Square area after scale(2.0): %.2f%n", sq.calculateArea());

        c.scale(2.0, 2.0);
        System.out.printf("Circle area after scale(2.0, 2.0): %.2f%n", c.calculateArea());

        Shape.printArea(c);
        Shape.printArea(sq);
        System.out.println("Circle ID: " + c.getShapeId());
        System.out.println("Square ID: " + sq.getShapeId());

        // Shape is abstract, so: new Shape() is not allowed.
    }
}
