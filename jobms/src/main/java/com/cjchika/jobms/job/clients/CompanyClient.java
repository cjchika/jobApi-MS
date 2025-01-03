package com.cjchika.jobms.job.clients;


import com.cjchika.jobms.job.external.Company;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "companyms", url = "${jobms.url}")
public interface CompanyClient {

    @GetMapping("/api/companies/{id}")
    Company getCompany(@PathVariable("id") Long id);
}
