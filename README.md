# Real-time Crash Detection 
Collect multiple indicators using the Sensor Logger app (Acceleration and Gyro sensor data)  
Link : https://play.google.com/store/apps/details?id=com.kelvin.sensorapp&hl=ko
   
<img src="https://github.com/LimHyunJune/sensor-real-time-data-pipeline/assets/48524793/41aa91ed-04e4-42c9-bb14-e8b61e016888" width="900" height="500"/>   
<br/><br/>
Using collision detection algorithms based on sensor data  <br/> 
Note : https://www.kci.go.kr/kciportal/ci/sereArticleSearch/ciSereArtiView.kci?sereArticleSearchBean.artiId=ART002277558

# Architecture   
<img src="https://github.com/LimHyunJune/real-time-crash-detection/assets/48524793/e0857653-39e2-4607-bd82-422cbfffd820" width="900" height="500"/>  
<br/><br/>
- Kafka Cluster : Confluent Kafka Community (7.x.x) <br/>
- Kafka Client : Apache kafka (3.7.0) <br/>
- SpringBoot <br/>
- Sensor Loggger App   <br/>

# Settings
- Create Adapter for Host-VM Connection
- VM Network Settings (Host-Only Ethernet Adapter)
- Fixed IP with VM's enp0s08
- Check connection to VM on host PC

     
<img src="https://github.com/LimHyunJune/sensor-real-time-data-pipeline/assets/48524793/2879b315-188c-40d4-8c35-af2227eb5f58" width="700" height="500"/>  
<img src="https://github.com/LimHyunJune/sensor-real-time-data-pipeline/assets/48524793/82d40b81-e0b2-4e03-9ba3-05fb0fcd737e" width="700" height="500"/>  
   
<img src="https://github.com/LimHyunJune/sensor-real-time-data-pipeline/assets/48524793/7afecea6-3255-4557-9c69-5c1dbf8ae46f" width="700" height="500"/>  
<img src="https://github.com/LimHyunJune/sensor-real-time-data-pipeline/assets/48524793/f57fe165-2f06-4234-8e5c-e3353b49afc6" width="700" height="500"/>


<img src="https://github.com/LimHyunJune/sensor-real-time-data-pipeline/assets/48524793/dc52918b-5e47-4474-a17f-05f949b314d1"  width="500" height="300"/>  
<img src="https://github.com/LimHyunJune/sensor-real-time-data-pipeline/assets/48524793/e849b850-ed3f-45f5-b27d-2e1a8d1b2364"  width="500" height="300"/>  

---
    
**Other tasks**
- Install ssh-server for ssh connection
- In the broker server settings file, specify a fixed IP for advertised.listeners
