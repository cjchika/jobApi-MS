package com.cjchika.jobms.job.impl;

import com.cjchika.jobms.job.Job;
import com.cjchika.jobms.job.JobRepository;
import com.cjchika.jobms.job.JobService;
import com.cjchika.jobms.job.clients.CompanyClient;
import com.cjchika.jobms.job.clients.ReviewClient;
import com.cjchika.jobms.job.dto.JobDTO;
import com.cjchika.jobms.job.external.Company;
import com.cjchika.jobms.job.external.Review;
import com.cjchika.jobms.job.mapper.JobMapper;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepo;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private CompanyClient companyClient;

    @Autowired
    private ReviewClient reviewClient;

//    private int attempt = 0;

    private JobDTO convertToDto(Job job){

        Company company = companyClient.getCompany(job.getCompanyId());

        List<Review> reviews = reviewClient.getReviews(job.getCompanyId());

        return JobMapper.mapToJobWithCompanyDto(job,company, reviews);
    }

    @Override
//    @CircuitBreaker(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
//    @Retry(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
    @RateLimiter(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
    public List<JobDTO> findAll() {
//        System.out.println("Attempt: " + ++attempt);
        List<Job> jobs = jobRepo.findAll();

        return jobs.stream().map(this::convertToDto).collect(Collectors.toList());
    }

//    public List<String> companyBreakerFallback(Exception e){
//        List<String> list = new ArrayList<>();
//        list.add("Dummy Fallback");
//        return list;
//    }

    @Override
    public void createJob(Job job) {
        jobRepo.save(job);
    }

    @Override
    public JobDTO getJobById(Long id) {
        Job job =  jobRepo.findById(id).orElse(null);

        return convertToDto(job);
    }

    @Override
    public boolean deleteJobById(Long id) {
        Optional<Job> jobOptional = jobRepo.findById(id);

        if (jobOptional.isPresent()) {
            jobRepo.deleteById(id);
            return true;
        }

        return false;
    }

    @Override
    public boolean updateJob(Long id, Job job) {
        Optional<Job> jobOptional = jobRepo.findById(id);

        if(jobOptional.isPresent()){
            Job existingJob = jobOptional.get();

            existingJob.setTitle(job.getTitle());
            existingJob.setDescription(job.getDescription());
            existingJob.setLocation(job.getLocation());
            existingJob.setMaxSalary(job.getMaxSalary());
            existingJob.setMinSalary(job.getMinSalary());
            existingJob.setCompanyId(job.getCompanyId());

            jobRepo.save(existingJob);
            return true;
        }
        return false;
    }
}
