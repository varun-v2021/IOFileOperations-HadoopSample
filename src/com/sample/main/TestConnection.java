package com.sample.main;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.io.IOUtils;

public class TestConnection {
	
	//TO CHECK THE FILES USE HDFS COMMANDS
	
	//~/Downloads/hadoop-2.10.0$ bin/hdfs dfs -ls /user
	
	//hdfs dfs -mkdir -p /wordcount/input/
	//bin/hdfs dfs -copyFromLocal /tmp/namenode/sample.txt /wordcount/input/
	
	public static void main(String[] args) throws IOException, URISyntaxException 
	{
		Configuration conf = new Configuration();
		FileSystem fileSystem = FileSystem.get(new URI("hdfs://localhost:54310"),conf);
		if(fileSystem instanceof DistributedFileSystem) {
			System.out.println("HDFS is the underlying filesystem");
		}
		else {
			System.out.println("Other type of file system "+fileSystem.getClass());
		}
		
		checkExists();
		createDirectory();
		checkExists();
		writeFileToHDFS();
		readData();
	}

	public static void createDirectory() throws IOException {
		Configuration configuration = new Configuration();
		configuration.set("fs.defaultFS", "hdfs://localhost:9000");
		FileSystem fileSystem = FileSystem.get(configuration);
		String directoryName = "javadeveloperzone/javareadwriteexample";
		Path path = new Path(directoryName);
		fileSystem.mkdirs(path);
	}

	public static void writeFileToHDFS() throws IOException {
		Configuration configuration = new Configuration();
		configuration.set("fs.defaultFS", "hdfs://localhost:9000");
		FileSystem fileSystem = FileSystem.get(configuration);
		//Create a path
		String fileName = "read_write_hdfs_example.txt";
		Path hdfsWritePath = new Path("/user/javadeveloperzone/javareadwriteexample/" + fileName);
		FSDataOutputStream fsDataOutputStream = fileSystem.create(hdfsWritePath,true);

		BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fsDataOutputStream,StandardCharsets.UTF_8));
		bufferedWriter.write("Java API to write data in HDFS");
		bufferedWriter.newLine();
		bufferedWriter.close();
		fileSystem.close();
	}

	public static void checkExists() throws IOException {
        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS", "hdfs://localhost:9000");
        FileSystem fileSystem = FileSystem.get(configuration);
        String directoryName = "javadeveloperzone/javareadwriteexample";
        Path path = new Path(directoryName);
        if(fileSystem.exists(path)){
            System.out.println("File/Folder Exists : "+path.getName());
        }else{
            System.out.println("File/Folder does not Exists : "+path.getName());
        }
    }
	
	public static void readData() throws URISyntaxException, IOException {
		//1. Get the instance of Configuration
		Configuration configuration = new Configuration();
		//2. URI of the file to be read
		URI uri = new URI("hdfs://localhost:9000/wordcount/input/sample.txt");
		//3. Get the instance of the HDFS 
		FileSystem hdfs = FileSystem.get(uri, configuration);
		//4. A reference to hold the InputStream
		InputStream inputStream = null;
		try{
			//5. Prepare the Path, i.e similar to File class in Java, Path represents file in HDFS
			Path path = new Path(uri);
			//6. Open a Input Stream to read the data from HDFS
			inputStream = hdfs.open(path);
			//7. Use the IOUtils to flush the data from the file to console
			IOUtils.copyBytes(inputStream, System.out, 4096, false);
		}finally{
			//8. Close the InputStream once the data is read
			IOUtils.closeStream(inputStream);
		}

	}
}

