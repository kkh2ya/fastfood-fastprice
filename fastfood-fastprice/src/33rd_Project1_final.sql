--초기 셋팅 SQL
--메뉴, 할인 메뉴, 사용자, 사용자 인덱스용 시퀀스, 사용자 보유 할일 목록, 사용자 최종 선택 table 생성
--전체 메뉴, 전체 할인 메뉴 등록
--author : 김기홍

-- 할인 메뉴 테이블 생성
create table salemenu(
saleindex varchar2(4) primary key,
saleway varchar2(200),
salemenu varchar2(200),
saleprice number(8),
salefrm date,
saledue date,
saletimesrt varchar2(6),
saletimeend varchar2(6)
);

commit;

-- 메뉴 테이블 생성
create table menu(
menu varchar2(80) unique not null,
price number(10) not null,
mainRecipe varchar2 (15) not null,
category varchar2 (15) not null
);

commit;

-- 사용자 테이블 생성
create table users(
name varchar2(25) not null,
birthday varchar2(15) not null,
id varchar2(25) unique not null,
password varchar2(10) not null,
userindex number primary key
);

commit;

--사용자 인덱스 용 시퀀스 생성
create sequence user_userindex_seq
start with 1
increment by 1;

commit;

--특정 사용자(사용자 인덱스로 넘버링)가 보유한 할인 가능 목록 테이블 생성
create table usersalelist(
userindex number references users(userindex) on delete cascade not null,
usersalelistindex varchar2(4) references salemenu(saleindex)
);

commit;

--사용자 최종선택 기록 테이블 생성
create table usersfinalchoice(
userid varchar2(25),
finalchoice varchar2(200)
);

commit;


--할인 메뉴 입력
insert into salemenu values('1.1', '영수증 세트 업그레이드 쿠폰', '단품 구매 시 세트 업그레이드 쿠폰', 0, '2017/01/01', '2017/12/31', '000000', '240000');

commit;

insert into salemenu values('2.1', '올마이쇼핑카드(점심)', '점심시간 음식점(버거킹 포함) 10% 청구할인', 0, '2017/01/01', '2017/12/31', '120000', '140000');

commit;

insert into salemenu values('2.2', '현대카드ZERO', '외식 : 모든 외식업종(버거킹 포함) 이용금액 1.2% 할인', 0, '2017/01/01', '2017/12/31', '000000', '240000');

commit;

insert into salemenu values('3.1', '시럽월렛', '와퍼 + 콜라', 4500, '2017/05/01', '2017/05/31', '100000', '220000');

commit;

insert into salemenu values('3.2', '시럽월렛', '치즈와퍼 + 콜라', 5000, '2017/05/01', '2017/05/31', '100000', '220000');

commit;

insert into salemenu values('3.3', '시럽월렛', '와퍼주니어 + 와퍼주니어 + 콜라 + 콜라', 6000, '2017/05/01', '2017/05/31', '100000', '220000');

commit;

insert into salemenu values('3.4', '시럽월렛', '콰트로치즈와퍼 + BLT롱치킨버거 + 콜라 + 콜라', 10000, '2017/05/01', '2017/05/31', '100000', '220000');

commit;

insert into salemenu values('3.5', '시럽월렛', '갈릭스테이크버거 + 콜라', 5500, '2017/05/01', '2017/05/31', '100000', '220000');

commit;

insert into salemenu values('3.6', '시럽월렛', '치즈프라이', 2000, '2017/05/01', '2017/05/31', '100000', '220000');

commit;

insert into salemenu values('3.7', '시럽월렛', '크런치치킨버거 + 콜라', 3900, '2017/05/01', '2017/05/31', '100000', '220000');

commit;

insert into salemenu values('3.8', '시럽월렛', '아이스아메리카노', 1000, '2017/05/01', '2017/05/31', '100000', '220000');

commit;

insert into salemenu values('3.9', '시럽월렛', '컵아이스크림', 500, '2017/05/01', '2017/05/31', '100000', '220000');

commit;

insert into salemenu values('3.10', '시럽월렛', '아메리카노(Hot)', 1000, '2017/05/01', '2017/05/31', '100000', '220000');

commit;

insert into salemenu values('4.1', '(공식) 버거킹 BURGERKINGKOREA', '불고기버거', 1000, '2017/05/15', '2017/05/31', '100000', '220000');

commit;

insert into salemenu values('4.2', '(공식) 버거킹 BURGERKINGKOREA', '와퍼주니어', 2000, '2017/05/15', '2017/05/31', '100000', '220000');

commit;

insert into salemenu values('4.3', '(공식) 버거킹 BURGERKINGKOREA', '와퍼 + 프렌치프라이 + 콜라', 4900, '2017/05/15', '2017/05/31', '100000', '220000');

commit;

insert into salemenu values('4.4', '(공식) 버거킹 BURGERKINGKOREA', '콜라', 0, '2017/05/15', '2017/05/31', '100000', '220000');

commit;

insert into salemenu values('4.5', '(공식) 버거킹 BURGERKINGKOREA', '너겟킹10조각', 1500, '2017/05/15', '2017/05/31', '100000', '220000');

commit;

insert into salemenu values('4.6', '(공식) 버거킹 BURGERKINGKOREA', '콰트로치즈와퍼주니어 + 프렌치프라이 + 콜라', 4500, '2017/05/15', '2017/05/31', '100000', '220000');

commit;

insert into salemenu values('4.7', '(공식) 버거킹 BURGERKINGKOREA', '갈릭스테이크버거 + 망고젤리에이드', 5500, '2017/05/15', '2017/05/31', '100000', '220000');

commit;

insert into salemenu values('4.8', '(공식) 버거킹 BURGERKINGKOREA', '콰트로치즈와퍼 + 통새우와퍼주니어 + 콜라 + 콜라 + 프렌치프라이', 10000, '2017/05/15', '2017/05/31', '100000', '220000');

commit;

insert into salemenu values('4.9', '(공식) 버거킹 BURGERKINGKOREA', '프렌치프라이 + 바닐라선데', 1500, '2017/05/15', '2017/05/31', '100000', '220000');

commit;

insert into salemenu values('4.10', '(공식) 버거킹 BURGERKINGKOREA', '아이스아메리카노', 1000, '2017/05/15', '2017/05/31', '100000', '220000');

commit;

insert into salemenu values('5.1', '버거킹이 드리는 감사의 달 선물', '콰트로치즈와퍼 + 치즈와퍼 + 콜라  + 콜라', 10000, '2017/05/15', '2017/06/18', '100000', '220000');

commit;

insert into salemenu values('5.2', '버거킹이 드리는 감사의 달 선물', '갈릭스테이크버거 + 치즈와퍼 + 콜라  + 콜라', 10000, '2017/05/15', '2017/06/18', '100000', '220000');

commit;

insert into salemenu values('5.3', '버거킹이 드리는 감사의 달 선물', '콰트로치즈와퍼 + BLT롱치킨버거 + 콜라 + 콜라', 10000, '2017/05/15', '2017/06/18', '100000', '220000');

commit;

insert into salemenu values('5.4', '버거킹이 드리는 감사의 달 선물', 'X-TRA크런치치킨 + 콜라', 4500, '2017/05/15', '2017/06/18', '100000', '220000');

commit;

insert into salemenu values('5.5', '버거킹이 드리는 감사의 달 선물', '치킨프라이 + 쉬림프치킨프라이', 3000, '2017/05/15', '2017/06/18', '100000', '220000');

commit;

insert into salemenu values('5.6', '버거킹이 드리는 감사의 달 선물', '와퍼주니어 + 콜라', 3900, '2017/05/15', '2017/06/18', '100000', '220000');

commit;

insert into salemenu values('5.7', '버거킹이 드리는 감사의 달 선물', '불고기버거', 1500, '2017/05/15', '2017/06/18', '100000', '220000');

commit;

insert into salemenu values('5.8', '버거킹이 드리는 감사의 달 선물', '아메리카노', 1000, '2017/05/15', '2017/06/18', '100000', '220000');

commit;

insert into salemenu values('5.9', '버거킹이 드리는 감사의 달 선물', '컵아이스크림', 500, '2017/05/15', '2017/06/18', '100000', '220000');

commit;


--메뉴 입력
insert into menu values('BLT롱치킨버거', 4900, 'chicken', 'burger');

commit;

insert into menu values('X-TRA크런치치킨', 4700, 'chicken', 'burger');

commit;

insert into menu values('갈릭스테이크버거', 6700, 'beef', 'burger');

commit;

insert into menu values('롱킹', 5900, 'beef', 'burger');

commit;

insert into menu values('불고기버거', 3000, 'beef', 'burger');

commit;

insert into menu values('불고기와퍼', 5600, 'beef', 'burger');

commit;

insert into menu values('불고기와퍼주니어', 4000, 'beef', 'burger');

commit;

insert into menu values('오리지널롱치킨버거', 4400, 'chicken', 'burger');

commit;

insert into menu values('와퍼', 5600, 'beef', 'burger');

commit;

insert into menu values('와퍼주니어', 4000, 'beef', 'burger');

commit;

insert into menu values('치즈버거', 3000, 'beef', 'burger');

commit;

insert into menu values('치즈와퍼', 6200, 'beef', 'burger');

commit;

insert into menu values('치즈와퍼주니어', 4300, 'beef', 'burger');

commit;

insert into menu values('콰트로치즈와퍼', 6500, 'beef', 'burger');

commit;

insert into menu values('콰트로치즈와퍼주니어', 4600, 'beef', 'burger');

commit;

insert into menu values('크런치치킨', 4300, 'chicken', 'burger');

commit;

insert into menu values('통새우스테이크버거', 7600, 'shrimp', 'burger');

commit;

insert into menu values('통새우와퍼', 6500, 'shrimp', 'burger');

commit;

insert into menu values('통새우와퍼주니어', 4600, 'shrimp', 'burger');

commit;

insert into menu values('햄버거', 2700, 'beef', 'burger');

commit;

insert into menu values('미닛메이드', 2500, 'else', 'drink');

commit;

insert into menu values('생수', 1200, 'else', 'drink');

commit;

insert into menu values('스프라이트', 1700, 'else', 'drink');

commit;

insert into menu values('아메리카노', 1500, 'else', 'drink');

commit;

insert into menu values('카페라떼', 2500, 'else', 'drink');

commit;

insert into menu values('콜라', 1700, 'else', 'drink');

commit;

insert into menu values('콜라제로', 1700, 'else', 'drink');

commit;

insert into menu values('핫초코', 2000, 'else', 'drink');

commit;

insert into menu values('환타오렌지', 1700, 'else', 'drink');

commit;

insert into menu values('너겟킹4조각', 2000, 'else', 'hotsnack');

commit;

insert into menu values('너겟킹6조각', 3000, 'else', 'hotsnack');

commit;

insert into menu values('너겟킹10조각', 5000, 'else', 'hotsnack');

commit;

insert into menu values('쉬림프치킨프라이', 2000, 'else', 'hotsnack');

commit;

insert into menu values('스위트포테이토프라이', 2600, 'else', 'hotsnack');

commit;

insert into menu values('어니어링', 2000, 'else', 'hotsnack');

commit;

insert into menu values('치즈프라이', 2500, 'else', 'hotsnack');

commit;

insert into menu values('치킨프라이', 2000, 'else', 'hotsnack');

commit;

insert into menu values('프렌치프라이', 1600, 'else', 'hotsnack');

commit;

insert into menu values('딸기선데', 1500, 'else', 'coldsnack');

commit;

insert into menu values('메이플선데', 1500, 'else', 'coldsnack');

commit;

insert into menu values('바닐라선데', 1500, 'else', 'coldsnack');

commit;

insert into menu values('스노우브라우니', 1300, 'else', 'coldsnack');

commit;

insert into menu values('스노우블루베리', 1000, 'else', 'coldsnack');

commit;

insert into menu values('아포가토', 2500, 'else', 'coldsnack');

commit;

insert into menu values('초코선데', 1500, 'else', 'coldsnack');

commit;

insert into menu values('컵아이스크림', 600, 'else', 'coldsnack');

commit;

insert into menu values('콘샐러드', 1600, 'else', 'coldsnack');

commit;

