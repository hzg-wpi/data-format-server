package hzg.wpn.tango;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.tango.client.ez.data.type.TangoImage;
import org.tango.client.ez.proxy.TangoProxies;
import org.tango.client.ez.proxy.TangoProxy;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

/**
 * @author khokhria
 * @since 6/17/15.
 */
public class DataFormatServerIT {

    private Path cwd;

    @Before
    public void before() throws Exception {
        cwd = Paths.get("target/var").toAbsolutePath();
        Files.deleteIfExists(Paths.get(cwd.toString(), "test.h5"));
    }


    //    @Test
    @Category(Integration.class)
    public void testWriteImage() throws Exception {
        TangoProxy mockCamera = TangoProxies.newDeviceProxyWrapper("tango://hzgctkit:10000/hzgctkit/imagedevice/0");

        TangoImage<short[]> image = mockCamera.readAttribute("Image");

//        server.setNxPath("/entry/data/data");
//        server.write16bitImage(image.getData());
    }

    @Test
    @Category(Integration.class)
    public void writeDoubleNan() throws Exception {
        TangoProxy dfs = TangoProxies.newDeviceProxyWrapper("tango://hzgxenvtest:10000/development/dfs/0");

        if(!dfs.readAttribute("cwd").equals(cwd.toString())){
            dfs.writeAttribute("cwd", cwd.toString());
        }

        assertEquals(cwd.toString(), dfs.readAttribute("cwd"));

//        dfs.executeCommand("openFile", "/home/khokhria/Projects/jDFS/target/var/test.h5");

        dfs.executeCommand("createFile", "test.h5");

        dfs.writeAttribute("nxPath", "/entry/instrument/detector/distance");
        dfs.executeCommand("writeDouble", Double.NaN);

        dfs.executeCommand("closeFile", null);
    }
}