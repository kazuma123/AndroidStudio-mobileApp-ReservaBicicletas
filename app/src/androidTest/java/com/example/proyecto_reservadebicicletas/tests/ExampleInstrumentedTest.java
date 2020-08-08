package com.example.proyecto_reservadebicicletas.tests;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.proyecto_reservadebicicletas.Usuario.PruebasLogin.PruebasLoginUsuario;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest extends TestCase {

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext;
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("Aplicacion Resrva de Bicicletas","com.example.proyecto_reservadebicicletas", appContext.getPackageName());
    }
    @Test
    public void prueba1() {
        assertEquals("echambir@unsa.edu.pe","echambirgmail.com");
        // Use the form:
        // assertEquals("Foo does not equals bar", "echambir@unsa.edu.pe", "echambirgmail.com");
        // instead

        assertEquals("123456","123456");
        // Use the form:
        // assertEquals("Foo does not equals bar", "123456", "123456");
        // instead
    }
    @Test
    public void prueba2() {
        PruebasLoginUsuario loginUsuario = new PruebasLoginUsuario("echambir@unsa.edu.pe", "123456");
        assertEquals("echambir@unsa.edu.pe", loginUsuario.getLogin());
        // Use the form:
        // assertEquals("Foo does not equals bar", "echambir@unsa.edu.pe", "loginUsuario.getLogin()");
        // instead

        assertEquals("123456", loginUsuario.getPass());
        // Use the form:
        // assertEquals("Foo does not equals bar", "123456", "loginUsuario.getPass()");
        // instead
    }
}
