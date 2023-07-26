package com.jeesite.modules.tab.web;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;

 import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.processing.OperationManager;
import com.qiniu.processing.OperationStatus;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.qiniu.util.StringUtils;
import com.qiniu.util.UrlSafeBase64;

public class HttpServletRequestUtils {


	  public	 static String PIEFIX = "https://file.twcreaotr.com/";

	static	String ACCESS_KEY = "K_VMGzXUQIj37-V0ajUDbKxXjuyiqr3WDyp6fXRM";
		static String SECRET_KEY = "DNkiCcfVTU2QmXxArYlpS8LH2iiYwveBYZffmhhU";
		// 要上传的空间
	public  static	String bucketname = "beimei22";
	
		static Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);

		private static String AMAPKEY = "282a21b70b0695a153223c4ea164ed98";// 高德导航的KEY

		
		
		
		
		 static Zone z = Zone.autoZone();
	     static Configuration c = new Configuration(z);

	     
	     public static void main(String[] args) {
//	    	 	pfopServide("9f168894-4ea4-4f30-814e-89093664b763.mp4");
	    	 
	   String   path =  	 uploadFiles(new File("/Users/gaofeng/Downloads/stankoCode/jeesite4/web/src/main/webapp/userfiles/fileupload/201806/1010399765458587648.png"));
	   
	   System.out.println(path);
	    	 	
		}
	     
	    //创建上传对象
	    static UploadManager uploadManager = new UploadManager(c);
		public static List<String> saveFileByMultipartFile(MultipartFile[] multipartFiles,
				HttpServletRequest httpServletRequest) {
			Map<String, String> map = new HashMap<String, String>();
			List<String>  list  =   new ArrayList<>() ; 
			
			String path = httpServletRequest.getSession().getServletContext().getRealPath("upload");

			for (MultipartFile file : multipartFiles) {
				
				String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "."
						+ file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
				
				File targetFile = new File(path, fileName);
				 
				try {
					file.transferTo(targetFile);
					
					 Response res = uploadManager.put(targetFile.getAbsoluteFile(), targetFile.getName(), getUpToken());
					 list.add(PIEFIX +  targetFile.getName()) ;
					 
					
				} catch (IllegalStateException e) {
					 
					e.printStackTrace();
				} catch (IOException e) {
					 
					e.printStackTrace();
				}
			}
			
	 
			return list;
		}

		 //简单上传，使用默认策略，只需要设置上传的空间名就可以了
	    public static String getUpToken() {
	        return auth.uploadToken(bucketname);
	    }

		public static Map<String,String> saveFileByMultipartFile(MultipartFile video,
				HttpServletRequest httpServletRequest) {
			
			Map<String,String>   map =    new HashMap<>()  ;
			

		    String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "."
					+ video.getOriginalFilename().substring(video.getOriginalFilename().lastIndexOf(".") + 1);
		    
		    String fileName1 = UUID.randomUUID().toString().replaceAll("-", "") + "."
					+ video.getOriginalFilename().substring(video.getOriginalFilename().lastIndexOf(".") + 1);
			String path = httpServletRequest.getSession().getServletContext().getRealPath("upload");

			File  t =  new  File(path);
			if (!t.exists()) {
				t.mkdirs();
			}
			
			File targetFile = new File(path, fileName);
			
			
			 
			try {
				video.transferTo(targetFile);
				
				 Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
				 Zone z = Zone.autoZone();
				    Configuration c = new Configuration(z);

				    //创建上传对象
				    
				    Response res = uploadManager.put(targetFile.getAbsoluteFile(), targetFile.getName(), getUpToken());
					 
					 map.put("url1", PIEFIX +  targetFile.getName()) ;
				    UploadManager uploadManager = new UploadManager(c);

				  
		 	    String urlbase64 = UrlSafeBase64.encodeToString(bucketname + ":"  +  fileName1);
		 	   String pfops = fops + "|saveas/" + urlbase64;
		 	   res = uploadManager.put(targetFile.getAbsolutePath(), null, getUpToken2(pfops));
	          //打印返回的信息
		 	  
		 	    map.put("url2", PIEFIX + fileName1) ;
				 
			}catch(Exception E){
				E.printStackTrace();
			}
			
			return map;
		}
		
		 static  String pipeline = "jiwen";
		public static  String   fops = "avthumb/mp4/ab/128k/ar/22050/acodec/libfaac/r/30/vb/300k/vcodec/libx264/s/320x240/autoscale/1/stripmeta/0";
		    //设置转码的队列
		
		  public static String getUpToken2(String pfops) {
		        return auth.uploadToken(bucketname, null, 3600, new StringMap()
		                .putNotEmpty("persistentOps", pfops)
		                .putNotEmpty("persistentPipeline", pipeline), true);
		    }

		public static List<String> saveFileByMultipartFile(MultipartFile[] multipartFiles,
				HttpServletRequest httpServletRequest, String token) {

	 		List<String>  list  =   new ArrayList<>() ; 
			
			String path = httpServletRequest.getSession().getServletContext().getRealPath("upload");

			for (MultipartFile file : multipartFiles) {
				
				String fileName = token+ "."
						+ file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
				
				File targetFile = new File(path, fileName);
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}
				try {
					file.transferTo(targetFile);
					
					 Response res = uploadManager.put(targetFile.getAbsoluteFile(), targetFile.getName(), getUpToken(targetFile.getName()));
					 list.add(PIEFIX +  targetFile.getName()) ;
					 
					
				} catch (IllegalStateException e) {
					 
					e.printStackTrace();
				} catch (IOException e) {
					 
					e.printStackTrace();
				}
			}
			
	 
			return list;
		
		}

		public static String getUpToken(String token) {
	              return auth.uploadToken(bucketname, token);

	      }
		public static String uploadFiles(File  f ) {
			String uuid =   UUID.randomUUID().toString()+"";
			 try {
				Response res = uploadManager.put(f.getAbsoluteFile(), uuid, getUpToken());
			} catch (QiniuException e) {
				e.printStackTrace();
			}
			 return  (PIEFIX + uuid) ;
	}


		
		 
		
	 public	 static String  pfopServide(String key){
	  
		 //待处理文件名
		  
		 Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
		 //数据处理指令，支持多个指令
		 String ss =  UUID.randomUUID().toString() ;
		 System.out.println(ss);
		 String saveMp4Entry = String.format("%s:" +key, bucketname);
		 
		 String avthumbMp4Fop = String.format("avthumb/mp4/ab/128k/ar/44100/acodec/libfaac/r/30/vb/600k/vcodec/libx264/s/480x360/autoscale/1/stripmeta/0|saveas/%s", UrlSafeBase64.encodeToString(saveMp4Entry));
	 	 //将多个数据处理指令拼接起来
		 String persistentOpfs = StringUtils.join(new String[]{
		         avthumbMp4Fop
		 }, ";");
		 //数据处理队列名称，必须
		 String persistentPipeline = "jiwen";
		 //数据处理完成结果通知地址
		 String persistentNotifyUrl = "http://www.fengyoucai.com/jiwen/common/getCallData";
		 //构造一个带指定Zone对象的配置类
		 Configuration cfg = new Configuration(Zone.zone0());
		 //...其他参数参考类注释
		 //构建持久化数据处理对象
		 OperationManager operationManager = new OperationManager(auth, cfg);
		 try {
		     String persistentId = operationManager.pfop(bucketname, key, persistentOpfs, persistentPipeline, persistentNotifyUrl, true);
		      
		     System.out.println(persistentId);
		     OperationStatus operationStatus = operationManager.prefop(persistentId);
		     
		     System.out.println(operationStatus.code);
		   
		 } catch (QiniuException e) {
		     System.err.println(e.response.toString());
		 }
		 
		 return ss  ;
		}
	 

	public  static   void deleteAll(String keys) {
		Configuration cfg = new Configuration(Zone.zone0());
		// ...其他参数参考类注释
		Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
		BucketManager bucketManager = new BucketManager(auth, cfg);
		try {
			bucketManager.delete(bucketname, keys);
		} catch (QiniuException e) {
			e.printStackTrace();
		}
	}
		 



}
class    LatLonPoint{
	private  double  latitude;
	private  double  longitude;
	public LatLonPoint(double retLat, double retLon) {
		this.latitude =  retLat; 
		this.longitude =  retLon;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	

}
