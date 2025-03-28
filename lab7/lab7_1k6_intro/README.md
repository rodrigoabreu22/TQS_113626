# Notes

test.js execution output without authorization token

```

         /\      Grafana   /‾‾/  
    /\  /  \     |\  __   /  /   
   /  \/    \    | |/ /  /   ‾‾\ 
  /          \   |   (  |  (‾)  |
 / __________ \  |_|\_\  \_____/ 

     execution: local
        script: test.js
        output: -

     scenarios: (100.00%) 1 scenario, 1 max VUs, 10m30s max duration (incl. graceful stop):
              * default: 1 iterations for each of 1 VUs (maxDuration: 10m0s, gracefulStop: 30s)

ERRO[0000] TypeError: Cannot read property 'name' of undefined
        at default (file:///home/rodrigoabreu/Desktop/LEI/TerceiroAno/semestre2/TQS/TQS_113626/lab7/lab7_1/k6-oss-workshop/test.js:20:35(44))  executor=per-vu-iterations scenario=default source=stacktrace


  █ TOTAL RESULTS 

    HTTP
    http_req_duration......................: avg=1.6ms  min=1.6ms  med=1.6ms  max=1.6ms  p(90)=1.6ms  p(95)=1.6ms 
    http_req_failed........................: 100.00% 1 out of 1
    http_reqs..............................: 1       333.116253/s

    EXECUTION
    iteration_duration.....................: avg=2.65ms min=2.65ms med=2.65ms max=2.65ms p(90)=2.65ms p(95)=2.65ms
    iterations.............................: 1       333.116253/s

    NETWORK
    data_received..........................: 166 B   55 kB/s
    data_sent..............................: 326 B   109 kB/s




running (00m00.0s), 0/1 VUs, 1 complete and 0 interrupted iterations
default ✓ [======================================] 1 VUs  00m00.0s/10m0s  1/1 iters, 1 per VU
```


with authorization token:
```

         /\      Grafana   /‾‾/  
    /\  /  \     |\  __   /  /   
   /  \/    \    | |/ /  /   ‾‾\ 
  /          \   |   (  |  (‾)  |
 / __________ \  |_|\_\  \_____/ 

     execution: local
        script: test.js
        output: -

     scenarios: (100.00%) 1 scenario, 1 max VUs, 10m30s max duration (incl. graceful stop):
              * default: 1 iterations for each of 1 VUs (maxDuration: 10m0s, gracefulStop: 30s)

INFO[0000] A Solid Pugliese (5 ingredients)              source=console


  █ TOTAL RESULTS 

    HTTP
    http_req_duration.......................................................: avg=57.31ms min=57.31ms med=57.31ms max=57.31ms p(90)=57.31ms p(95)=57.31ms
      { expected_response:true }............................................: avg=57.31ms min=57.31ms med=57.31ms max=57.31ms p(90)=57.31ms p(95)=57.31ms
    http_req_failed.........................................................: 0.00% 0 out of 1
    http_reqs...............................................................: 1     16.998686/s

    EXECUTION
    iteration_duration......................................................: avg=58.6ms  min=58.6ms  med=58.6ms  max=58.6ms  p(90)=58.6ms  p(95)=58.6ms 
    iterations..............................................................: 1     16.998686/s

    NETWORK
    data_received...........................................................: 671 B 11 kB/s
    data_sent...............................................................: 365 B 6.2 kB/s




running (00m00.1s), 0/1 VUs, 1 complete and 0 interrupted iterations
default ✓ [======================================] 1 VUs  00m00.1s/10m0s  1/1 iters, 1 per VU
```

- The API call took **57.31ms**.
- 1 request was made.
- 0 requests failed.