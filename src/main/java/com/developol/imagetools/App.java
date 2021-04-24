package com.developol.imagetools;

import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;


public class App 
{
    public static void main( String[] args )
    {
        if (args == null || args.length == 0) {
            System.out.println("No path provided in java args.");
            System.exit(1);
        }
        String dir = args[0];
        File dirFile = new File(dir);
        for (File file : dirFile.listFiles()) {

            try {
                final JpegImageMetadata metadata = (JpegImageMetadata) Imaging.getMetadata(file);
                String[] timeStamps = metadata.getExif().getFieldValue(ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL);
                if (timeStamps == null || timeStamps.length == 0) {
                    throw new RuntimeException();
                }

                String timeStamp = timeStamps[0];
                timeStamp = timeStamp.replace(" ", "_")
                        .replace(":","");
                String newFileName = timeStamp + "_" + file.getName();

                Path sourcePath = file.toPath();

                Files.move(sourcePath, sourcePath.resolveSibling(newFileName));
                System.out.println("File " + file.getName() + " was successfully renamed to " + newFileName + ".");

            } catch (Exception e) {
                System.out.println("There was a problem with file " + file.getName() + ".");
            }
        }

    }
}
