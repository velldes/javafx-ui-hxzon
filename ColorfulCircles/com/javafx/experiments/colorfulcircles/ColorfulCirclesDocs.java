/*
 * Copyright (c) 2008, 2011 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle Corporation nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.javafx.experiments.colorfulcircles;

import static java.lang.Math.random;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BoxBlur;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * ColorfulCircles
 *
 */
public class ColorfulCirclesDocs extends Application {

    @Override public void start(Stage stage) {
        // create scene
        Scene scene = new Scene(new Group(),800,600,Color.BLACK);
        stage.setScene(scene);
        // create first list of circles
        Group layer1 = new Group();
        for(int i=0; i<15;i++) {
            Circle circle = new Circle(200,Color.web("white",0.05f));
            circle.setStrokeType(StrokeType.OUTSIDE);
            circle.setStroke(Color.web("white",0.2f));
            circle.setStrokeWidth(4f);
            layer1.getChildren().add(circle);
        }
        // create second list of circles
        Group layer2 = new Group();
        for(int i=0; i<20;i++) {
            Circle circle = new Circle(70,Color.web("white",0.05f));
            circle.setStrokeType(StrokeType.OUTSIDE);
            circle.setStroke(Color.web("white",0.1f));
            circle.setStrokeWidth(2f);
            layer2.getChildren().add(circle);
        }
        // create third list of circles
        Group layer3 = new Group();
        for(int i=0; i<10;i++) {
            Circle circle = new Circle(150,Color.web("white",0.05f));
            circle.setStrokeType(StrokeType.OUTSIDE);
            circle.setStroke(Color.web("white",0.16f));
            circle.setStrokeWidth(4f);
            layer3.getChildren().add(circle);
        }
        // Set a blur effect on each layer
        layer1.setEffect(new BoxBlur(30,30,3));
        layer2.setEffect(new BoxBlur(2,2,2));
        layer3.setEffect(new BoxBlur(10,10,3));
        // create a rectangle size of window with colored gradient
        Rectangle colors = new Rectangle(scene.getWidth(), scene.getHeight(),
                new LinearGradient(0f,1f,1f,0f,true, CycleMethod.NO_CYCLE,
                        new Stop(0,Color.web("#f8bd55")),
                        new Stop(0.14f,Color.web("#c0fe56")),
                        new Stop(0.28f,Color.web("#5dfbc1")),
                        new Stop(0.43f,Color.web("#64c2f8")),
                        new Stop(0.57f,Color.web("#be4af7")),
                        new Stop(0.71f,Color.web("#ed5fc2")),
                        new Stop(0.85f,Color.web("#ef504c")),
                        new Stop(1,Color.web("#f2660f")))
        );
        // create main content
        Group outerCircleGroup = new Group(
                new Group(new Rectangle(scene.getWidth(), scene.getHeight(), Color.BLACK), layer1, layer2, layer3),
                colors
        );
        colors.setBlendMode(BlendMode.OVERLAY);
        ((Group)scene.getRoot()).getChildren().add(outerCircleGroup);
        // show stage
        stage.show();
        // create list of all circles
        List<Node> allCircles = new ArrayList<Node>();
        allCircles.addAll(layer1.getChildren());
        allCircles.addAll(layer2.getChildren());
        allCircles.addAll(layer3.getChildren());
        // Create a animation to randomly move every circle in allCircles
        Timeline timeline = new Timeline();
        for(Node circle: allCircles) {
            timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO, // set start position at 0s
                    new KeyValue(circle.translateXProperty(),random()*800),
                    new KeyValue(circle.translateYProperty(),random()*600)
                ),
                new KeyFrame(new Duration(40000), // set end position at 40s
                    new KeyValue(circle.translateXProperty(),random()*800),
                    new KeyValue(circle.translateYProperty(),random()*600)
                )
            );
        }
        timeline.setAutoReverse(true);
        timeline.setCycleCount(Animation.INDEFINITE);
        // play 40s of animation
        timeline.play();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
