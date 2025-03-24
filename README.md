# Team02

https://diligent-pentagon-bf8.notion.site/HR-BANK-1b556a0cb3b080268b3cf709bcab04b4?pvs=4

# 팀원 구성
<table>
    <tr align="center">
        <td><a href="https://github.com/eunseobb">신은섭</a></td>
        <td><a href="https://github.com/yxoni">박서연</a></td>
        <td><a href="https://github.com/goospel">김시온</a></td>
        <td><a href="https://github.com/naron88">이병규</a></td>
        <td><a href="https://github.com/byeongyeol12">공별열</a></td>
    </tr>
    <tr align="center">
        <td>
            <img src="https://avatars.githubusercontent.com/u/92570258?v=4" width="130">
        </td>
        <td>
            <img src="https://avatars.githubusercontent.com/u/90109410?v=4" width="130">
        </td>
        <td>
            <img src="https://avatars.githubusercontent.com/u/80618017?v=4" width="130">
        </td>
        <td>
            <img src="https://avatars.githubusercontent.com/u/93171052?v=4" width="130">
        </td>
        <td>
            <img src="https://avatars.githubusercontent.com/u/132568348?v=4" width="130">
        </td>
    </tr>
     <tr align="center">
        <td><B>BE</B></td>
        <td><B>BE</B></td>
        <td><B>BE</B></td>
        <td><B>BE</B></td>
        <td><B>BE</B></td>
    </tr>
</table>

# 프로젝트 소개
Batch로 데이터를 관리하는 Open EMS의 Spring 백엔드 시스템 구축

## 프로젝트 기간: 2025.3.13 ~ 2024.03.24

## 기술 스택

Backend: Spring Boot, Spring Data JPA, Spring Scheduler

Database: PostgreSQL

공통 Tool: Git & Github, Discord

## 팀원별 구현 기능 상세

### 신은섭
- 데이터 백업 목록 조회
- 데이터 백업 생성
- 최근 백업 정보 조회
### 공병열
- 부서 목록 조회
- 부서 등록
- 부서 상세 조회
- 부서 삭제
- 부서 수정
### 김시온
- 직원 정보 수정 이력 목록 조회
- 직원 정보 수정 이력 상세 조회
- 수정 이력 건수 조회
### 박서연
- 직원 목록 조회
- 직원 등록
- 직원 상세 조회
- 직원 수정
- 직원 삭제
### 이병규
- 직원 수 추이 조회
- 직원 분포 조회
- 직원 수 조회
- 파일 다운로드
- 프로필, 백업 데이터, 에러 로그 저장
## 파일 구조
```test
src
 ┣ main
 ┃ ┣ java
 ┃ ┃ ┣ com
 ┃ ┃ ┃ ┣ hrbank
 ┃ ┃ ┃ ┃ ┣ config
 ┃ ┃ ┃ ┃ ┃ ┣ JPAConfig.java
 ┃ ┃ ┃ ┃ ┣ controller
 ┃ ┃ ┃ ┃ ┃ ┣ BackupController.java
 ┃ ┃ ┃ ┃ ┃ ┣ ChangeLogController.java
 ┃ ┃ ┃ ┃ ┃ ┣ DepartmentController.java
 ┃ ┃ ┃ ┃ ┃ ┣ EmployeeController.java
 ┃ ┃ ┃ ┃ ┃ ┣ EmployeeDashboardController.java
 ┃ ┃ ┃ ┃ ┃ ┣ FileManagementController.java
 ┃ ┃ ┃ ┃ ┣ dto
 ┃ ┃ ┃ ┃ ┃ ┣ backup
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ BackupDto.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ CursorPageResponseBackupDto.java
 ┃ ┃ ┃ ┃ ┃ ┣ changeLog
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ ChangeLogCreateRequest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ ChangeLogDto.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ ChangeLogRequestDto.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ CursorPageResponseChangeLogDto.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ DiffDto.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ ErrorResponseDto.java
 ┃ ┃ ┃ ┃ ┃ ┣ dashboard
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ EmployeeDistributionDto.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ EmployeeTrendDto.java
 ┃ ┃ ┃ ┃ ┃ ┣ department
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ DepartmentCreateRequest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ DepartmentDto.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ DepartmentUpdateRequest.java
 ┃ ┃ ┃ ┃ ┃ ┣ employee
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ CursorPageResponseEmployeeDto.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ EmployeeCreateRequest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ EmployeeDto.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ EmployeeUpdateRequest.java
 ┃ ┃ ┃ ┃ ┣ entity
 ┃ ┃ ┃ ┃ ┃ ┣ Backup.java
 ┃ ┃ ┃ ┃ ┃ ┣ ChangeLog.java
 ┃ ┃ ┃ ┃ ┃ ┣ Department.java
 ┃ ┃ ┃ ┃ ┃ ┣ Employee.java
 ┃ ┃ ┃ ┃ ┃ ┣ Metadata.java
 ┃ ┃ ┃ ┃ ┣ exception
 ┃ ┃ ┃ ┃ ┃ ┣ GlobalExceptionHandler.java
 ┃ ┃ ┃ ┃ ┣ mapper
 ┃ ┃ ┃ ┃ ┃ ┣ BackupMapper.java
 ┃ ┃ ┃ ┃ ┃ ┣ EmployeeMapper.java
 ┃ ┃ ┃ ┃ ┣ repository
 ┃ ┃ ┃ ┃ ┃ ┣ BackupRepository.java
 ┃ ┃ ┃ ┃ ┃ ┣ ChangeLogRepository.java
 ┃ ┃ ┃ ┃ ┃ ┣ DepartmentRepository.java
 ┃ ┃ ┃ ┃ ┃ ┣ EmployeeRepository.java
 ┃ ┃ ┃ ┃ ┃ ┣ MetadataRepository.java
 ┃ ┃ ┃ ┃ ┣ scheduler
 ┃ ┃ ┃ ┃ ┃ ┣ BackupScheduler.java
 ┃ ┃ ┃ ┃ ┣ service
 ┃ ┃ ┃ ┃ ┃ ┣ stats
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ EmployeeDistributionService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ EmployeeStatisticsService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ EmployeeTrendService.java
 ┃ ┃ ┃ ┃ ┃ ┣ BackupService.java
 ┃ ┃ ┃ ┃ ┃ ┣ ChangeLogService.java
 ┃ ┃ ┃ ┃ ┃ ┣ DashboardService.java
 ┃ ┃ ┃ ┃ ┃ ┣ DepartmentService.java
 ┃ ┃ ┃ ┃ ┃ ┣ EmployeeService.java
 ┃ ┃ ┃ ┃ ┃ ┣ MetadataService.java
 ┃ ┃ ┃ ┃ ┣ storage
 ┃ ┃ ┃ ┃ ┃ ┣ local
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ LocalBinaryContentStorage.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ LocalEmployeesStorage.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ LocalLogFileStorage.java
 ┃ ┃ ┃ ┃ ┃ ┣ BinaryContentStorage.java
 ┃ ┃ ┃ ┃ ┃ ┣ EmployeesStorage.java
 ┃ ┃ ┃ ┃ ┣ util
 ┃ ┃ ┃ ┃ ┃ ┣ CursorPaginationUtils.java
 ┃ ┃ ┃ ┃ ┣ converter
 ┃ ┃ ┃ ┃ ┃ ┣ MultipartJackson2HttpMessageConverter.java
```
## 구현 홈페이지
https://sb01-hrbank-team02-dev-production.up.railway.app/
