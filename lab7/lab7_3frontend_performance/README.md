# Notes

## b)
Results report: [here](lighthouse_rebort_ex3b.pdf)

## c)

#### What metrics are contributing the most to the frontend perceived performance? What do they mean? 

Metrics:
![alt text](image.png)

- **First Contentful Paint**: First Contentful Paint marks the time at which the first text or image is painted. (contributed with worse score for performance)
- **Largest Contentful Paint**: Largest Contentful Paint marks the time at which the largest text or image is painted. (contributed with worse score for performance)
- **Total Blocking Time**: Sum of all time periods between FCP and Time to Interactive, when task length exceeded 50ms, expressed in milliseconds.
- **Cumulative Layout Shift**: Measures the movement of visible elements within the viewport. 
- **Speed Index**: Shows how quickly the contents of a page are visibly populated.


#### How would you make the site more accessible?
I would increase the contrast ratio between between background and foreground colors.

Indicator:
![alt text](image-1.png)

## d)
Results report: [here](lighthouse_report_ex3d.pdf)

Results show a slight improvement in performance. The total blocking time on this test was 50. The previous one was 80. An improvement of less 30 ms.

![alt text](image-3.png)

The difference was that noticeable probably because I don't use chrome and had to install it only for the exercise. So there was no cookies interfering on the first test.

## e)
Results report: [here](lighthouse_report_ex3e.pdf)

There was a huge improvement on performance using desktop tests. 100% score on performance now. The other fields stayed the same.

![alt text](image-4.png)

### Found the following reasons:

#### Hardware and software 
- Mobile devices have less processing power, memory, and storage than desktop computers.
- Mobile devices often use wireless networks, which can be less stable than wired connections.

#### Network connectivity 
- Mobile devices often use wireless networks, which can be less stable and have lower bandwidth than wired connections.


## f)

- **Performance**: Testing performance ensures your site loads quickly, enhancing user experience and engagement. 
- **Accessibility**: Accessibility testing ensures inclusivity, allowing all users, including those with disabilities, to navigate and use your site effectively. 
- **Best Practices**: Best practices improve site reliability, security, and maintainability. 
- **SEO Testing**: SEO testing boosts visibility on search engines, attracting more traffic and ensuring your content reaches the right audience. 

Each aspect contributes to a better, more impactful web presence!


## g)
These lighthouse tests were both made on incognito mode:

Results report on mobile mode: [here](lighthouse_report_site_ua_mobile.pdf)

Results report on desktop mode: [here](lighthouse_report_site_ua_desktop.pdf)

- Both test results reveal that performance is the major issue on UA website, with a score of 37/36 which is very low. 
- The desktop version has slightly worse acessibility (88/82) and SEO (92/85). 
- Best Pratices stayed the same on both (96).

In the results pdf, a list of the main performance problems can be found.

#### How easy is it to fix issues in a site that has been live for a long time?
Fixing issues in a site that has been live for a long time may be challemnging. We may have to face the following challenges: 
- **Legacy Code & Dependencies** – Outdated libraries/frameworks may require major refactoring.
- **Complexity & Technical Debt** – Accumulated inefficiencies make optimizations harder without breaking features.
- **Third-Party Integrations** – External scripts can slow performance but may be difficult to remove if essential.
- **SEO & UX Risks** – Changes in structure or content can impact rankings and user experience; thorough testing is crucial.

#### Could this be avoided and how? 
This could have been avoided if these tests were applied regularly during development and, consequnetly, the code being optimized accordingly. 
Here is some aspects that could have improved this:
- Follow Performance Best Practices from the Start
- Regularly Audit and Optimize the Site
- Monitor Third-Party Scripts
- Use Scalable Architecture


