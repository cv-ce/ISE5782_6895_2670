package unittests;

import static java.awt.Color.BLUE;
import static java.awt.Color.PINK;
import static java.awt.Color.YELLOW;
import static java.awt.Color.GREEN;
import static java.awt.Color.MAGENTA;
import static java.awt.Color.CYAN;
import static java.awt.Color.RED;



import org.junit.jupiter.api.Test;

import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.Color;
import primitives.Double3;
import primitives.Material;
import primitives.Point;
import primitives.Vector;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

class OurTests {
	private Scene scene = new Scene("Test scene");
	private Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
			.setViewPlaneSize(200, 200).setDistance(1000) //
			.setRayTracer(new RayTracerBasic(scene));
	@Test
	void test() {
		
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), new Double3(0.15)));

		scene.geometries.add( //
				new Triangle(new Point(0, 55, 20), new Point(30, 50, 30), new Point(-30, 50, 30)) //
						.setMaterial(new Material().setKs(0.8).setShininess(60)).setEmission(new Color(YELLOW)), //
				new Triangle(new Point(55, 0, 20), new Point(50, 30, 30), new Point(50, -30, 30)) //
						.setMaterial(new Material().setKs(0.8).setShininess(100)).setEmission(new Color(PINK)), //
				new Triangle(new Point(-55, 0, 20), new Point(-50, 30, 30), new Point(-50, -30, 30)) //
						.setMaterial(new Material().setKs(0.8).setShininess(100)).setEmission(new Color(PINK)), //
				new Triangle(new Point(0, -55, 20), new Point(30, -50, 30), new Point(-30, -50, 30)) //
						.setMaterial(new Material().setKs(0.8).setShininess(60)).setEmission(new Color(YELLOW)), //
				new Sphere(new Point(0, 0, -11), 30d) //
						.setEmission(new Color(GREEN)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),//
				new Sphere(new Point(0, 100, 10), 20d) //
						.setEmission(new Color(BLUE)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),
				new Sphere(new Point(0, -100, 10), 20d) //
						.setEmission(new Color(BLUE)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),
				new Sphere(new Point(100, 0, 10), 20d) //
						.setEmission(new Color(RED)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),
				new Sphere(new Point(-100, 0, 10), 20d) //
						.setEmission(new Color(RED)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),
				new Sphere(new Point(50, 50, 0), 10d) //
						.setEmission(new Color(MAGENTA)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),//
				new Sphere(new Point(50, -50, 0), 10d) //
						.setEmission(new Color(CYAN)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),//
				new Sphere(new Point(-50, 50, 0), 10d) //
						.setEmission(new Color(CYAN)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),//
				new Sphere(new Point(-50, -50, 0), 10d) //
						.setEmission(new Color(MAGENTA)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30))//
						);
		
		scene.lights.add( //
				new SpotLight(new Color(700, 400, 400), new Point(40, 40, 115), new Vector(-1, -1, -4)) //
						.setKl(4E-4).setKq(2E-5));

		camera.setImageWriter(new ImageWriter("OurImage", 600, 600)) //
				.renderImage() //
				.writeToImage();
		
}

}
