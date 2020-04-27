# IOFileOperations-HadoopSample

To run the application

## WordCount - mvn clean package - will create a executable jar file which can be run using

./hadoop jar /home/varun/eclipse-workspace/HadoopSample/target/HadoopSample-0.0.1-SNAPSHOT.jar com.sample.main.WordCount ./user/input/ ./user/output2/

## TestConnection - is a java application which can be directly run after modifying I/O as per system where it is executing

SINGLE NODE HADOOP CLUSTER, ACCESSING DATA USING HDFS URI AND SETUP RELATED

HDFS namenode, datanode, nodemanager and resourcemanager server activation

As per step 7, we will have our hdfs started on /tmp/datanode and /tmp/namenode  (directory of root or at kernel level /tmp)

1. install java jdk 8 and export JAVA_HOME and PATH=$PATH:$JAVA_HOME
2. Download the Hadoop 2.10.0 Package and extract it (any version of hadoop as per requirement)
3. vi ~/.bashrc and add

JAVA_HOME='/home/varun/Downloads/jdk-8u251-linux-x64/jdk1.8.0_251'
export JAVA_HOME
PATH="$JAVA_HOME/bin:$PATH"
export PATH
export HADOOP_HOME='/home/varun/Downloads/hadoop-2.10.0/share/hadoop'

4. source ~/.bashrc
5. goto <hadoop package extracted dir>/etc/hadoop
6. edit core-site.xml with following contents

<configuration>
<property>
 <name>fs.default.name</name>
 <value>hdfs://localhost:9000</value>
 </property>
</configuration>


7. edit hdfs-site.xml

<configuration>
<property>
 <name>dfs.replication</name>
 <value>1</value>
</property>
<property>
<name>dfs.permission</name>
<value>false</value>
</property>
<property>
<name>dfs.namenode.name.dir</name>
<value>file:///home/varun/Downloads/hadoopdata/namenode</value>
</property>
<property>
<name>dfs.datanode.data.dir</name>
<value>file:///home/varun/Downloads/hadoopdata/datanode</value>
</property>
</configuration>


8. cp mapred-site.xml.template mapred-site.xml
9. edit mapred-site.xml

<configuration>
<property>
<name>mapreduce.framework.name</name>
<value>yarn</value>
</property>
</configuration>

10. edit yarn-site.xml

<property>
<name>yarn.nodemanager.aux-services</name>
<value>mapreduce_shuffle</value>
</property>
<property>
<name>yarn.nodemanager.auxservices.mapreduce.shuffle.class</name>
<value>org.apache.hadoop.mapred.ShuffleHandler</value>
</property>
</configuration>

11. edit hadoop-env.sh and override JAVA_HOME

export JAVA_HOME='/home/varun/Downloads/jdk-8u251-linux-x64/jdk1.8.0_251'

WHENEVER ANY CONFIG CHANGE IS REQUIRED, DO THE CONFIG CHANGE AND EXECUTE BELOW 3 COMMANDS.
NOTE - ON FORMATTING THE NAMENODE, ALL DATA WILL BE WIPED OF, SO MAKE SURE A BACKUP IS TAKEN

12. cd <hadoop package extracted dir>/sbin and execute

./stop-all.sh

13. cd <hadoop package extracted dir> and execute

bin/hadoop namenode -format

14. cd <hadoop package extracted dir>/sbin and execute

./start-all.sh

15. To check whether namenode is up and running, use this in browser

http://localhost:50070/dfshealth.html#tab-overview

also

sudo netstat -ntlp


OTHER COMMANDS

To get contents of the HDFS

//TO CHECK THE FILES USE HDFS COMMANDS
	
cd ~/Downloads/hadoop-2.10.0 
bin/hdfs dfs -ls /user
bin/hdfs dfs -mkdir -p /wordcount/input/
bin/hdfs dfs -copyFromLocal /tmp/namenode/sample.txt /wordcount/input/

TO ACCESS HDFS DATA FROM URI FROM JAVA CODE

hdfs://localhost:9000/wordcount/input/sample.txt

References:

https://www.folkstalk.com/2013/06/connect-to-hadoop-hdfs-through-java.html
https://javadeveloperzone.com/hadoop/java-read-write-files-hdfs-example/
https://stackoverflow.com/questions/42033601/hadoop-cannot-see-my-input-directory
https://www.alibabacloud.com/blog/how-to-setup-a-single-node-hadoop-file-system-cluster-on-ubuntu_595549
