using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using System.Timers;

namespace AutoDeleteEvents
{
    class Program
    {
        static HttpClient client = new HttpClient();
        static void Main(string[] args)
        {
            RunAsync().Wait();
        }
        static async Task RunAsync()
        {
            client.BaseAddress = new Uri("https://urban-action.max-karthan.de/TinderAPI/api/");
            client.DefaultRequestHeaders.Accept.Clear();
            //client.DefaultRequestHeaders.Authorization = new System.Net.Http.Headers.AuthenticationHeaderValue("Bearer", "Mxmbz3PUaXQIVet0ioaZY31TfjpdEprJE-sdOV7O6c2ibUdYSGq7GBcPxTrwnTKu0IuH_fGdgRJrLN-0LrKwVnEJoquTH284RKuJJKHiY_DNaHgHQTH5lW1kLFamINvekv-HA5YyMyh4WnhtKksvzGAEoWNFfxMntd_skx_ZgTuQleBCRqUXkQxVk7_2e3d09iCjxeTubBq5hvdtFNoOOvhVtEG-hKSTx8MXUSxj4jtQBv_qYE6dxM7eqLytnZkDHdSz3pkzCzJOle-4MMzF7Hv-IyjHImVB2x13e8vWpfLAfmYUPti6zdKzOcUUggETS69cXzaOzCB6eSh1egWRMJMdiRuivGwk8AcEirXEHT0G1naCBJuz3aioGUQ8-T6vGihV5-s1WAg8boh_WbQq-y5E-bo6wU-yxJHvmcivl5cQe1TljGb5dheit-L2vSQrkevuyAgyaLMpeVonyPiSa71cJEPsoYMilbtZl1BRsts");
            client.DefaultRequestHeaders.Accept.Add(new System.Net.Http.Headers.MediaTypeWithQualityHeaderValue("application/json"));

            Timer timer = new Timer();
            timer.Elapsed += new ElapsedEventHandler(AutoDelete);
            timer.Interval = 1000;
            timer.Start();
            Console.ReadKey();
            
            
        }

        static async void AutoDelete(object source,ElapsedEventArgs e)
        {
            HttpResponseMessage response = await client.DeleteAsync("event/autodelete");
            if (response.IsSuccessStatusCode)
            {
                Console.WriteLine(DateTime.Now.ToString()+": checked all events.");
            }
            else
            {
                Console.WriteLine("Error: "+response.StatusCode);
                
            }
        }
    }
}
