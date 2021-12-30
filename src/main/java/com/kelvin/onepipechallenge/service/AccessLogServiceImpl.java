package com.kelvin.onepipechallenge.service;

import com.kelvin.onepipechallenge.data.model.Log;
import com.kelvin.onepipechallenge.data.repository.AccessLogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


@Service
public class AccessLogServiceImpl implements AccessLogService{

    @Autowired
    AccessLogsRepository accessLogRepository;

    @Override
    public List<Log> collectLogsFromAccessLogFile(File file) throws FileNotFoundException {
        List<Log> logs = new ArrayList<>();
        Scanner reader = new Scanner(file);
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            Scanner read = new Scanner(line);
            read.useDelimiter("\\|");
            while (read.hasNext()) {
                Log log = new Log();
                String date = read.next();
                date = date.replace(" ", "T");
                log.setStartDate(LocalDateTime.parse(date));
                log.setIp(read.next());
                log.setMethod(read.next());
                log.setCode(read.next());
                log.setClient(read.next());
                logs.add(log);
            }
            read.close();
        }
        reader.close();
        return logs;
    }
}