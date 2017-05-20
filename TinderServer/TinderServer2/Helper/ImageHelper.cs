using System;
using System.Collections.Generic;
using System.Drawing;
using System.Drawing.Imaging;
using System.IO;
using System.Linq;
using System.Web;

namespace TinderServer2.Helper
{
    public static class ImageHelper
    {
        public static string createName()
        {
            string imgName = DateTime.Now.ToString();
            imgName = imgName.Replace(" ", "_");
            imgName = imgName.Replace(":", String.Empty);
            imgName = imgName.Replace(".", "-");

            return imgName;
        }

        public static string FixBase64ForImage(string Image)
        {
            System.Text.StringBuilder sbText = new System.Text.StringBuilder(Image, Image.Length);
            sbText.Replace("\r\n", String.Empty);
            sbText.Replace(" ", String.Empty);
            return sbText.ToString();
        }

        public static string GetExtension(byte [] imageBytes )
        {
            var image = Image.FromStream(new MemoryStream(imageBytes));

            if (ImageFormat.Jpeg.Equals(image.RawFormat))
            {
                return ".jpg";
            }
            else if (ImageFormat.Png.Equals(image.RawFormat))
            {
                return ".png";
            }
            else
            {
                return String.Empty;
            }
        }

        public static void SaveImage (byte [] imageBytes, string filePath)
        {
            using (var imageFile = new FileStream(filePath, FileMode.Create))
            {
                imageFile.Write(imageBytes, 0, imageBytes.Length);
                imageFile.Flush();
            }
        }
    }
}