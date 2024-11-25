# 💡 개요
## 판매자와 구매자 간 거래를 중개하는 쇼핑몰 서버 구축 프로젝트

<img src="https://img.shields.io/badge/Java-007396?style=flat-square&logo=Java&logoColor=white"/></a>
<img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=flat-square&logo=SpringBoot&logoColor=white"/></a>
<img src ="https://img.shields.io/badge/MariaDB-003545?style=flat-square&logo=mariadb&logoColor=white"/></a>
<img src="https://img.shields.io/badge/AWS-232F3E?style=flat-square&logo=AmazonAWS&logoColor=white"/></a> 
<img src="https://img.shields.io/badge/-Swagger-%23Clojure?style=flat-square&logo=swagger&logoColor=white"/></a>
<img src="https://img.shields.io/badge/Postman-FF6C37?style=flat-square&logo=Postman&logoColor=white"/></a>

## 공통
- [x] 회원가입
 - 모든 사용자는 회원 가입 시 기본적으로 USER 권한(일반권한)/ 판매자로 승인 받을시 (셀러권한)을 지닌다.
 - 비밀번호는 security에서 제공하는 passwordEncoder 사용하여 보안 강화 
- [x] 비밀번호 재설정 
  - 이메일 검증 후 비밀번호 재설정 
- [x] 로그인 JWT 토큰 발행
- [x] security 보안강화
 - 각 요청 마다 토큰으로 유효성 판단 후 응답 

### 이메일
- [x] 회원가입 후 인증메일 전송
 - 이메일 인증 해야만 로그인을 할 수 있다.
- [x] 주문내역 이메일로 발송하기


## 셀러
- [x] 상품 등록
 - 판매자만 상품을 등록할 수 있다.
- [x] 상품 수정
 - 해당 상품을 올린 판매자만 상품을 수정할 수있다.
- [x] 상품 삭제
 - 해당 상품을 올린 판매자만 삭제할 수 있다. 

## 구매자
- [x] 장바구니에 물건 추가
 - 로그인 한 사용자만 장바구니추가를 할 수 있다.
 - 동일한 상품을 추가로 넣으면 수량이 올라가게 한다.
- [x] 장바구니 확인
 - 장바구니에 넣은 날짜순으로 정렬된다.
- [x] 주문하기

## 상품
- [x] 상품 목록
  - 상품은 최신순으로 기본 정렬되며 판매순으로도 정렬이 가능하다.
  - 로그인 안한 상태에서도 목록을 볼 수 있다.
- [x] 상품 검색 및 상세페이지
  - 로그인하지 않은 사용자를 포함한 모든 사용자는 상품을 검색 또는 상세페이지를 볼 수 있다.
  - 상세페이지안에서만 장바구니담기가 가능하다.
  - 상세페이지안에서만 리뷰를 볼 수 있다.

## 리뷰
- [x] 해당 상품 리뷰 (등록, 삭제, 수정)
- 해당 상품을 구매한 구매자만 등록 및 수정, 삭제를 할 수 있다.
- 상세페이지을 누르면 리뷰가 보여지게 한다.
- 댓글은 최신순으로 정렬되며 paging처리를 한다.

## 주문
- [x] 주문요청
  - 주문요청하면 주문이 바로 완료되면서 주문내역 이메일로 발송 


## ERD 시각화 
<img width="900" alt="스크린샷 2024-11-19 오후 4 07 26" src="https://github.com/user-attachments/assets/5867903a-c201-4d1e-a2fd-9b61434c6bc6">