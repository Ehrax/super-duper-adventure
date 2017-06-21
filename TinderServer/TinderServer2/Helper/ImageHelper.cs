﻿using System;
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
        public enum ImageType { EVENTIMAGE, PROFILEIMAGE };

        public static string LoadImageToBase64(string imagePath)
        {
            string base64String = null;
            if (!String.IsNullOrEmpty(imagePath))
            {

                try
                {
                    using (Image eventImage = Image.FromFile(imagePath))
                    {
                        using (MemoryStream ms = new MemoryStream())
                        {
                            eventImage.Save(ms, eventImage.RawFormat);
                            byte[] imageBytes = ms.ToArray();
                            base64String = Convert.ToBase64String(imageBytes);

                        }
                    }

                }
                catch (Exception e) when (e is OutOfMemoryException || e is FileNotFoundException || e is ArgumentException)
                {
                    base64String = null;
                }
            }

            return base64String;
        }

        public static string SaveBase64ImageToFileSystem(string base64Image, ImageType imageType, string imageTitle)
        {
            if (!String.IsNullOrEmpty(base64Image))
            {
                var imageBytes = Convert.FromBase64String(FixBase64ForImage(base64Image));

                string extension = GetExtension(imageBytes);

                if (extension.Equals(String.Empty))
                {
                    return ("NotAImage");
                }

                string appPath = HttpContext.Current.Request.MapPath(HttpContext.Current.Request.ApplicationPath);
                string filePath = null;
                switch (imageType)
                {
                    case ImageType.EVENTIMAGE:
                        filePath = appPath + "\\images\\events\\" + imageTitle + "_" + createName() + extension;
                        break;
                    case ImageType.PROFILEIMAGE:
                        filePath = appPath + "\\images\\user\\" + imageTitle + "_" + createName() + extension;
                        break;
                }

                SaveImage(imageBytes, filePath);

                return filePath;

            }
            return null;
        }



        private static string createName()
        {
            string imgName = DateTime.Now.ToString();
            imgName = imgName.Replace(" ", "_");
            imgName = imgName.Replace(":", String.Empty);
            imgName = imgName.Replace(".", "-");

            return imgName;
        }

        private static string FixBase64ForImage(string Image)
        {
            System.Text.StringBuilder sbText = new System.Text.StringBuilder(Image, Image.Length);
            sbText.Replace("\r\n", String.Empty);
            sbText.Replace(" ", String.Empty);
            return sbText.ToString();
        }

        private static string GetExtension(byte[] imageBytes)
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

        private static void SaveImage(byte[] imageBytes, string filePath)
        {
            using (var imageFile = new FileStream(filePath, FileMode.Create))
            {
                imageFile.Write(imageBytes, 0, imageBytes.Length);
                imageFile.Flush();
            }
        }

        


    }
}