(ns rts.gl)

(import '(java.awt Frame)
        '(java.awt.event WindowAdapter)
        '(javax.media.opengl GL GL2 GLEventListener)
        '(javax.media.opengl.awt GLCanvas)
        '(javax.media.opengl.glu GLU))

(def canvas (new GLCanvas))
(def frame (new Frame "RTS"))
(def glu (new GLU))

(defn exit []
  (.remove frame canvas)
  (.dispose frame))

(.addGLEventListener canvas
  (proxy [GLEventListener] []
    (init [drawable]
      (doto (.getGL drawable)
        (.glClearColor (double 1.0) 0 0 0)
        (.glClearDepth 1)))

    (display [drawable]
      (doto (.getGL drawable)
        (.glClear (. GL GL_COLOR_BUFFER_BIT))
        (.glClear (. GL GL_DEPTH_BUFFER_BIT))
        (.glLoadIdentity)
        (.glTranslatef 0 0 -5)

        (.glBegin (. GL GL_TRIANGLES))

        ; Front
        (.glColor3f 0 1 1)
        (.glVertex3f 0 1 0)
        (.glColor3f 0 0 1)
        (.glVertex3f -1 -1 1)
        (.glColor3f 0 0 0)
        (.glVertex3f 1 -1 1)

        ; Right Side Facing Front
        (.glColor3f 0 1 1)
        (.glVertex3f 0 1 0)
        (.glColor3f 0 0 1)
        (.glVertex3f 1 -1 1)
		    (.glColor3f 0 0 0)
		    (.glVertex3f 0 -1 -1)
		
		    ; Left Side Facing Front
		    (.glColor3f 0 1 1)
		    (.glVertex3f 0 1 0)
		    (.glColor3f 0 0 1)
		    (.glVertex3f 0 -1 -1)
		    (.glColor3f 0 0 0)
		    (.glVertex3f -1 -1 1)
		
		    ;Bottom
		    (.glColor3f 0 0 0)
		    (.glVertex3f -1 -1 1)
		    (.glColor3f 0.1 0.1 0.1)
		    (.glVertex3f 1 -1 1)
		    (.glColor3f 0.2 0.2 0.2)
		    (.glVertex3f 0 -1 -1)
		
		    (.glEnd)))

    (reshape [drawable x y w h]
      (when (> h 0)
        (let [gl2 (.getGL2 (.getGL drawable))]
          (.glMatrixMode gl2 (. GL2 GL_PROJECTION))
          (.glLoadIdentity gl2)
          (.gluPerspective glu (double 50.0) (double (/ w h)) (double 1.0) (double 1000.0))
          (.glMatrixMode gl2 (. GL2 GL_MODELVIEW))
          (.glLoadIdentity gl2)
          (.glViewport gl2 0 0 w h))))

    (dispose [drawable])))

(defn initGL []
  (doto frame
    (.add canvas)
    (.setSize 640 480)
    ;(.setUndecorated true)
    ;(.setExtendedState (. Frame MAXIMIZED_BOTH))
    (.addWindowListener
      (proxy [WindowAdapter] []
        (windowClosing [e] (exit))))
    (.setVisible true))
		(.requestFocus canvas))