# Lab8_3

![alt text](image.png)

- 0 coverage, I applied 0 tests

- Good maintainability.
- Major security problems.
## Main issues

These are the main issues.
- 1

![alt text](image-1.png)
![alt text](image-2.png)

- 2
![alt text](image-3.png)

I thought this was the best practice, but with SonarQube I found out that I should initialize the service in the constructor and use @Autowired annotation on the constructor.

- 3
![alt text](image-4.png)

Best practice:
![alt text](image-5.png)


- 4
![alt text](image-6.png)
This was reported as a major issue but i don't think it was an issue at all. That variable would not have any value. We should look at this with critical thinking too.


## Security
![alt text](image-7.png)

Secrets on the code??? What were thinking half an year ago...
