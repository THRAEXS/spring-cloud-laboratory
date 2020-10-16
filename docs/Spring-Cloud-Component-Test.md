# Test

Copy:
```sh
$ scp -r hanzo@10.122.22.115:/home/hanzo/Workspace/MicroServices/thraex-framework .
$ scp hanzo@10.122.22.115:/home/hanzo/Workspace/MicroServices/thraex-framework/thraex-gateway/target/gateway-1.0.0.jar .
```

Running:
```sh
$ mvn spring-boot:run \
	-DCFG_CONFIG_HOST=10.122.22.115 \
	-Deureka.client.serviceUrl.defaultZone=http://10.122.22.115:8761/eureka
```

```sh
$ java -jar gateway-1.0.0.jar \
	--CFG_CONFIG_HOST=10.122.22.115 \
	--eureka.client.serviceUrl.defaultZone=http://10.122.22.115:8761/eureka	
```



```sh
$ mvn clean package -Dmaven.test.skip=true docker:build
```

```sh
docker tag 10.122.22.115/thraex-registry 10.122.22.115:5000/thraex-registry
docker tag 10.122.22.115/thraex-config 10.122.22.115:5000/thraex-config
docker tag 10.122.22.115/thraex-gateway 10.122.22.115:5000/thraex-gateway
docker tag 10.122.22.115/thraex-admin10.122.22.115:5000/thraex-admin
```

```sh
$ docker run -d --name thraex-registry -p 8761:8761 10.122.22.115:5000/thraex-registry

$ docker run -d --name thraex-config -p 8750:8750 \
	-e CFG_EUREKA_HOST=10.122.22.115 \
	10.122.22.115:5000/thraex-config \
	; docker logs -f thraex-gateway

$ docker run -d --name thraex-gateway -p 8765:8765 \
	-e CFG_CONFIG_HOST=10.122.22.115 \
	-e SPRING_CLOUD_CONFIG_LABEL=env \
	10.122.22.115:5000/thraex-gateway \
	; docker logs -f thraex-gateway

$ docker run -d --name thraex-admin -p 8085:8085 \
	-e CFG_CONFIG_HOST=10.122.22.115 \
	-e SPRING_CLOUD_CONFIG_LABEL=env \
	10.122.22.115:5000/thraex-admin \
	; docker logs -f thraex-admin
```

```sh
docker rm -v $(docker stop thraex-registry)
docker rm -v $(docker stop thraex-config)
docker rm -v $(docker stop thraex-gateway)
docker rm -v $(docker stop thraex-admin)

docker rmi $(docker images 10.122.22.115/* -q)
```

```sh
docker ps -a --filter "name=thraex-*"
docker rm -v $(docker ps -a --filter "name=thraex-*" -q)
```

```sh
docker images -f "reference=10.122.22.115/thraex-*"
docker rmi -f $(docker images -f "reference=10.122.22.115/thraex-*" -q)
```

## Docker Settings

- /etc/systemd/system/docker.service.d/http-proxy.conf
```
[Service]
Environment="HTTP_PROXY=http://proxy1.bj.petrochina:8080"
Environment="HTTPS_PROXY=http://proxy1.bj.petrochina:8080" "NO_PROXY=localhost, 127.0.0.1, 10.27.213.66, 10.27.213.69, 10.122.22.115"
```

- /etc/docker/daemon.json
```
{
  "registry-mirrors": ["https://ik8akj45.mirror.aliyuncs.com"],
  "insecure-registries": ["10.27.213.66:5000", "10.122.22.115:5000"]
}
```

- /var/lib/docker/containers/CONTAINER_ID/hostconfig.json
```
# update port mapping
{
	...
	"PortBindings":{"5000/tcp":[{"HostIp":"","HostPort":"5000"}]}
	...
}
```

- http: server gave HTTP response to HTTPS client
```sh
$ vim /etc/docker/daemon.json
{ 
	"insecure-registries":["10.122.22.115:5000"] 
}

$ sudo systemctl daemon-reload
$ sudo systemctl restart docker
```

- net/http: request canceled (Client.Timeout exceeded while awaiting headers)
```sh
$ vim /etc/docker/daemon.json
{
	"registry-mirrors": ["https://ik8akj45.mirror.aliyuncs.com"]
    # "registry-mirrors":["https://docker.mirrors.ustc.edu.cn"]
}
```

## Questions

- spring.cloud.client.ip-address[10.27.213.167/172.16.81.167]
- spring.cloud.client.ip-address[10.27.213.167/172.16.81.167]
