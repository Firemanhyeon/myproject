package com.yedam;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class YdBrdMain {

	public static void main(String[] args) {
		YdBrdDao dao = new YdBrdDao();
		Scanner sc = new Scanner(System.in);
		Ydbrd dao1 = new Ydbrd();
		int menu = 0;
		int start = 0;
		int end = 5;
		int choice;
		String id1 = "";

		// 로그인창
		while (true) {
			System.out.println("1.로그인  2.회원가입  3. 아이디/비밀번호 찾기");
			try {
				int sel = Integer.parseInt(sc.nextLine());
				if (sel == 1) {
					System.out.println("아이디를 입력하세요");
					String id = sc.nextLine();
					id1 = id;
					System.out.println("비밀번호를 입력하세요");
					String pw = sc.nextLine();
					if (dao.login(id, pw)) {
						if (id.equals("admin")) {
							System.out.println("관리자 로그인 성공");
							break;
						}
						System.out.println("로그인성공!");

						break;
					} else {
						System.out.println("로그인실패.");
						continue;
					}
				} else if (sel == 2) {
					System.out.println("◆아이디를 입력하세요◆");
					String newid = sc.nextLine();
					System.out.println("◆비밀번호를 입력하세요◆");
					String newpw = sc.nextLine();
					System.out.println("◆이메일을 입력하세요◆");
					String newemail = sc.nextLine();
					dao1.setId(newid);
					dao1.setPw(newpw);
					dao1.setEmail(newemail);
					if (dao.newid(dao1)) {
						System.out.println("◆회원가입완료◆");
						continue;
					} else {
						System.out.println("◆회원가입 실패◆");
						continue;
					}
				} else if (sel == 3) {
					System.out.println("◆아이디 입력◆");
					String id = sc.nextLine();
					System.out.print("◆메일 입력◆");
					String to = sc.nextLine();

					if (dao.srchid(id, to)) {
						String title = "비밀번호 재설정";
						Random r = new Random();
						int num = r.nextInt(10000);
						String strNum = String.format("%04d", num);
						String content = strNum;// random숫자
						dao.pwrename(content);
						StringBuilder sb = new StringBuilder();
						sb.append("<p>비밀번호 초기화 안내 메일</p>");
						sb.append("<p>");
						sb.append("새로운 비밀번호는: " + content + " 입니다");
						sb.append("</p>");
						sb.append("<b>Good Bye!!</b>");
						content = sb.toString();

						SendMail mail = new SendMail();
						mail.sendMail(to, title, content);
					} else {
						System.out.println("◆아이디,이메일이 일치하지않습니다◆");
					}

				} else {
					System.out.println("◆잘못입력했습니다◆");
				}

			} catch (Exception e) {
				System.out.println("◆숫자를입력하세요◆");
			}
		}
		// 로그인창 끝

		// 메인화면
		while (true) {
			List<Ydbrd> list = dao.list();

			if (list.size() == 0) {
				System.out.println("◆작성된 글이 없습니다◆");
			} else {
				System.out.println("===================글 목록==================");

				for (int i = start; i < end; i++) {
					if (i < list.size()) {
						System.out.println("글 번호: " + list.get(i).getBrdnum() + " | 제목: " + list.get(i).getTitle()
								+ " | 작성날짜: " + list.get(i).getDate());
					}
				}

			}

			System.out.println("==========================================");
			System.out.println("1.글쓰기 | 2.글 선택 | 3.이전페이지 | 4.다음페이지 | 5.검색 | 6.내가 쓴 글 목록 | 7.설정 ");
			System.out.println("선택> ");
			try {
				menu = Integer.parseInt(sc.nextLine());

				// 글쓰기
				if (menu == 1) {
					System.out.println("◆제목을 입력하세요> ");
					String title = sc.nextLine();
					System.out.println("◆내용을 입력하세요> ");
					String content = sc.nextLine();
					dao1.setTitle(title);
					dao1.setContent(content);
					if (dao.add(dao1)) {
						System.out.println("◆등록 완료◆");
					} else {
						System.out.println("◆등록 실패◆");

					}
				}

				// 상세보기
				else if (menu == 2) {
					System.out.println("◆선택할 글 번호를 입력해주세요◆");
					int num = Integer.parseInt(sc.nextLine());
					boolean in = true;
					while (in) {
						Ydbrd brd1 = dao.deepsrc(num);
						if (brd1 == null) {
							System.out.println("◆해당글이 없습니다◆");
							break;
						}
						System.out.println("===================" + num + "번 글 입니다===================");
						System.out.println("글번호: " + brd1.getBrdnum() + "           제목: " + brd1.getTitle() + " \n내용: "
								+ brd1.getContent() + " \n날짜: " + brd1.getDate() + "   작성자: " + brd1.getId()
								+ "   조회수: " + brd1.getCount() + "  좋아요: " + brd1.getLike());
						System.out.println("====================댓글==========================");
						List<Ydbrd> list2 = dao.comment();
						if (list2.size() == 0) {
							System.out.println("◆작성된 댓글이 없습니다◆");
						} else {
							for (Ydbrd brd2 : list2) {
								System.out.println("댓글번호: " + brd2.getCommentnum() + " |댓글: " + brd2.getComment()
										+ " |아이디: " + brd2.getId() + " |날짜: " + brd2.getDate());
							}
						}
						if (dao.checklike.contains(dao.srcnum)) {
							System.out.println("1.댓글작성 | 2.수정 | 3.삭제 | 4.돌아가기 | 5.좋아요해제 | 6.댓글삭제");
							choice = Integer.parseInt(sc.nextLine());
						} else {

							System.out.println("1.댓글작성 | 2.수정 | 3.삭제 | 4.돌아가기 | 5.좋아요 | 6.댓글삭제");
							choice = Integer.parseInt(sc.nextLine());

						}
						// 댓글작성
						if (choice == 1) {
							System.out.println("◆댓글내용을입력하세요◆");
							String comm = sc.nextLine();
							dao1.setComment(comm);
							if (dao.commentmethod(dao1)) {
								System.out.println("◆등록 완료◆");
							} else {
								System.out.println("◆등록 실패◆");

							}

							// 수정
						} else if (choice == 2) {
							System.out.println("◆수정할 내용을 입력해주세요◆");
							String content = sc.nextLine();
							Ydbrd modify = new Ydbrd();
							modify.setContent(content);
							if (id1.equals("admin")) {
								dao.managermodify(modify);
								System.out.println("◆관리자권한 수정 완료◆");
								break;
							}
							if (dao.modify(modify)) {
								System.out.println("◆수정완료◆");
							} else {
								System.out.println("◆수정권한이 없습니다◆");
							}
						}
						// 삭제
						else if (choice == 3) {
							if (id1.equals("admin")) {
								dao.managerdelete();
								System.out.println("◆관리자권한 삭제완료◆");
								break;
							} else {
								if (dao.delete()) {
									System.out.println("◆삭제되었습니다◆");
									break;
								} else {
									System.out.println("◆삭제권한이 없습니다◆");
								}

							}
							// 돌아가기
						} else if (choice == 4) {
							break;
						}
						// 좋아요
						else if (choice == 5) {
							dao.like();
							continue;
						}
						// 댓글 삭제
						else if (choice == 6) {
							System.out.println("◆삭제할 댓글번호를 입력하세요◆");
							int comdel = Integer.parseInt(sc.nextLine());
							Ydbrd comdel1 = new Ydbrd();
							if (dao.comdelete(comdel)) {
								System.out.println("◆삭제완료◆");
							} else {
								System.out.println("◆삭제실패◆");
							}
						}
					}

					// 이전페이지
				} else if (menu == 3) {
					if (start > 4 || end > 5) {
						start = start - 5;
						end = end - 5;
						continue;
					} else {
						System.out.println("◆첫페이지입니다◆");
						continue;
					}

					// 다음페이지
				} else if (menu == 4) {
					if (list.size() >= end) {
						start = end;
						end = end + 5;
						continue;
					} else {
						System.out.println("◆마지막페이지입니다◆");
						continue;
					}

					// 검색
				} else if (menu == 5) {
					System.out.println("◆검색하실 제목을 입력해주세요◆");
					String sctitle = sc.nextLine();
					dao1.setTitle(sctitle);
					List<Ydbrd> sclist = dao.sclist(dao1);

					System.out.println("================검색결과=====================");
					for (Ydbrd sclist1 : sclist) {
						System.out.println("글 번호: " + sclist1.getBrdnum() + "  제목: " + sclist1.getTitle() + "  작성날짜: "
								+ sclist1.getDate());

					}
					System.out.println("\n");

					// 내가쓴글목록
				} else if (menu == 6) {
					List<Ydbrd> mylist = dao.mylist();
					System.out.println("================내가쓴글=====================");
					for (Ydbrd mylist1 : mylist) {
						System.out.println("글 번호: " + mylist1.getBrdnum() + "  |제목: " + mylist1.getTitle() + "  |작성날짜: "
								+ mylist1.getDate());
					}
					System.out.println("\n");
				}

				// 설정
				else if (menu == 7) {
					System.out.println("1.비밀번호 변경");
					int sel = Integer.parseInt(sc.nextLine());

					// 비밀번호변경
					if (sel == 1) {
						System.out.println("◆변경할 비밀번호를 입력하세요◆");
						String repw = sc.nextLine();
						if (dao.repw(repw)) {
							System.out.println("◆변경완료◆");
						} else {
							System.out.println("◆변경실패◆");
						}
					}
				}
			} catch (Exception e) {
				System.out.println("◆번호를 입력하세요◆");
			}

		}

	}
}
