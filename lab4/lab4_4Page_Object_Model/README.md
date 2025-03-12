To make this work I had to execute the followimng steps: 
- Type this command: 
```
sudo nano /lib/systemd/system/docker.service
```
then comment this line: 
```
ExecStart=/usr/bin/dockerd -H fd:// --containerd=/run/containerd/containerd.sock
```
and insert this line:
```
ExecStart=/usr/bin/dockerd --host=tcp://0.0.0.0:2375 --host=unix:///var/run/docker.sock
```
save the alterations. Then the following two commands in the terminal:
``` 
sudo systemctl daemon-reload
sudo systemctl restart docker
```

Then run the tests:
```
mvn test
```