package unittests;

import static java.awt.Color.BLUE;
import static java.awt.Color.WHITE;
import static java.awt.Color.PINK;
import static java.awt.Color.YELLOW;
import static java.awt.Color.GREEN;
import static java.awt.Color.ORANGE;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

import geometries.Geometry;
import geometries.Polygon;
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

	@Test
	void test() {
		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setViewPlaneSize(200, 200).setDistance(1000);

		scene.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));
		
		Material material = new Material().setKd(0.5).setKs(0.5).setShininess(300);
		Geometry triangle1 = new Triangle(new Point(0, 0, 0), new Point(50, 0, 50), new Point(0, 0, 50)).setMaterial(material);
		Geometry triangle2 = new Triangle(new Point(0, 0, 0), new Point(-50, 0, -50), new Point(-0, 0, -50)).setMaterial(material);
		
		scene.geometries.add( //
				triangle1, triangle2,
				new Triangle(new Point(100, 100, 100), new Point(100, 100, 0), new Point(100, -100, 100)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
				new Triangle(new Point(-100, -100, -100), new Point(0, -100, -100), new Point(-100, 0, -100)) //
						.setMaterial(new Material().setKd(0.9).setKs(0.9).setShininess(60)), //
				new Sphere(new Point(60, 50, -50), 10d).setEmission(new Color(GREEN)) //
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)),
				new Sphere(new Point(-20, 10, -50), 50d).setEmission(new Color(ORANGE)) //
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)),
				new Polygon(new Point(-10, -10, 0), new Point(10, -10, 0), new Point(10, 10, 0), new Point(-10, 10, 0)).setEmission(new Color(ORANGE)) //
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));

		scene.lights.add(new SpotLight(new Color(1000, 100, 100), new Point(-200, -200, 0), new Vector(1, 0, 0)) //
				.setKl(4E-5).setKq(2E-7));

		ImageWriter imageWriter = new ImageWriter("ourImage", 600, 600);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage();	}

}
