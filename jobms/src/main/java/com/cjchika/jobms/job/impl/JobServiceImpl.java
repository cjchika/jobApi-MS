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

    private JobDTO convertToDto(Job job){

//        Company company = restTemplate.getForObject(
//                "http://companyms:8082/api/companies/" + job.getCompanyId(),
//                Company.class);

//        ResponseEntity<List<Review>> reviewResponse = restTemplate.exchange(
//                "http://reviewms:8083/api/reviews?companyId=" + job.getCompanyId(),
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<Review>>() {
//                });

        //         List<Review> reviews = reviewResponse.getBody();

        Company company = companyClient.getCompany(job.getCompanyId());

        List<Review> reviews = reviewClient.getReviews(job.getCompanyId());

        return JobMapper.mapToJobWithCompanyDto(job,company, reviews);
    }

    @Override
    public List<JobDTO> findAll() {
        List<Job> jobs = jobRepo.findAll();

        return jobs.stream().map(this::convertToDto).collect(Collectors.toList());
    }

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
