package com.cjchika.jobms.job.impl;

import com.cjchika.jobms.job.Job;
import com.cjchika.jobms.job.JobRepository;
import com.cjchika.jobms.job.JobService;
import com.cjchika.jobms.job.dto.JobWithCompanyDTO;
import com.cjchika.jobms.job.external.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepo;

    private JobWithCompanyDTO convertToDto(Job job){
        RestTemplate restTemplate = new RestTemplate();

        JobWithCompanyDTO jobWithCompanyDTO = new JobWithCompanyDTO();

        jobWithCompanyDTO.setJob(job);

        Company company = restTemplate.getForObject(
                "http://localhost:8082/api/companies/" + job.getCompanyId(),
                Company.class);
        jobWithCompanyDTO.setCompany(company);

        return  jobWithCompanyDTO;
    }

    @Override
    public List<JobWithCompanyDTO> findAll() {
        List<Job> jobs = jobRepo.findAll();

        return jobs.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void createJob(Job job) {
        jobRepo.save(job);
    }

    @Override
    public Job getJobById(Long id) {
        return jobRepo.findById(id).orElse(null);
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
