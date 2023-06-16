package com.yedam;

import java.util.List;
import java.util.Scanner;

public class YdBrdMain {

	public static void main(String[] args) {
		YdBrdDao dao = new YdBrdDao();
		Scanner sc = new Scanner(System.in);
		Ydbrd dao1 = new Ydbrd();
		int menu = 0;
		int start = 0;
		int end = 5;
		
		String id1 = "";
		while (true) {
			System.out.println("1.로그인  2.회원가입");
			int sel = Integer.parseInt(sc.nextLine());
			if(sel ==1) {
				System.out.println("아이디를 입력하세요");
				String id = sc.nextLine();
				id1 = id;
				System.out.println("비밀번호를 입력하세요");
				String pw = sc.nextLine();
				if (dao.login(id, pw)) {
					if(id.equals("admin")) {
						System.out.println("관리자 로그인 성공");
						break;
					}
					System.out.println("로그인성공!");
					
					break;
				} else {
					System.out.println("로그인실패.");
					continue;
				}
			}
			else if(sel ==2) {
				System.out.println("아이디를 입력하세요");
				String newid = sc.nextLine();
				System.out.println("비밀번호를 입력하세요");
				String newpw = sc.nextLine();
				dao1.setId(newid);
				dao1.setPw(newpw);
				if(dao.newid(dao1)) {
					System.out.println("회원가입완료");
					continue;
				}
				else {
					System.out.println("회원가입 실패");
					continue;
				}
			}
			else {
				System.out.println("잘못 입력했습니다");
				continue;
			}

		}

		while (true) {
			List<Ydbrd> list = dao.list();

			if (list.size() ==0) {
				System.out.println("작성된 글이 없습니다");
			} else {
				System.out.println("================================");

				for (int i = start; i < end; i++) {
					if (i < list.size()) {
						System.out.println("글 번호: " + list.get(i).getBrdnum() + "  제목: " + list.get(i).getTitle()
								+ "  작성날짜: " + list.get(i).getDate());
					}
				}

			}

			System.out.println("================================");
			System.out.println("1.글쓰기 2.글 선택 3.이전페이지 4.다음페이지");
			System.out.println("선택> ");
			menu = Integer.parseInt(sc.nextLine());

			if (menu == 1) {
				System.out.println("제목을 입력하세요> ");
				String title = sc.nextLine();
				System.out.println("내용을 입력하세요> ");
				String content = sc.nextLine();
				dao1.setTitle(title);
				dao1.setContent(content);
				if (dao.add(dao1)) {
					System.out.println("등록 완료");
				} else {
					System.out.println("등록 실패");

				}
			}

			else if (menu == 2) {
				System.out.println("선택할 글 번호를 입력해주세요");
				int num = Integer.parseInt(sc.nextLine());
				boolean in = true;
				while (in) {
					Ydbrd brd1 = dao.deepsrc(num);
					if(brd1==null) {
						System.out.println("해당글이 없습니다");
						break;
					}
					System.out.println("===================" + num + "번 글 입니다==================");
					System.out.println("글번호: " + brd1.getBrdnum() + "           제목: " + brd1.getTitle() + " \n내용: "
							+ brd1.getContent() + " \n날짜: " + brd1.getDate() + "   작성자: " + brd1.getId() + "   조회수: "
							+ brd1.getCount()+ "  좋아요: "+ brd1.getLike());
					System.out.println("====================댓글==========================");
					List<Ydbrd> list2 = dao.comment();
					if (list2.size() == 0) {
						System.out.println("작성된 글이 없습니다");
					} else {
						for (Ydbrd brd2 : list2) {
							System.out.println("댓글번호: " + brd2.getCommentnum() + " 댓글: " + brd2.getComment() + " 아이디: "
									+ brd2.getId() + " 날짜: " + brd2.getDate());
						}
					}
					System.out.println("1.댓글작성 2.수정 3.삭제 4. 돌아가기 5.좋아요 ");
					int choice = Integer.parseInt(sc.nextLine());
					
					//댓글작성
					if (choice == 1) {
						System.out.println("댓글내용을입력하세요");
						String comm = sc.nextLine();
						dao1.setComment(comm);
						if (dao.commentmethod(dao1)) {
							System.out.println("등록 완료");
						} else {
							System.out.println("등록 실패");

						}
						
					//수정
					} else if (choice == 2) {
						System.out.println("수정할 내용을 입력해주세요");
						String content = sc.nextLine();
						Ydbrd modify = new Ydbrd();
						modify.setContent(content);
						if(id1.equals("admin")) {
							dao.managermodify(modify);
							System.out.println("관리자권한 수정 완료");
							break;
						}
						if (dao.modify(modify)) {
							System.out.println("수정완료!");
						} else {
							System.out.println("수정권한이 없습니다");
						}
					}
					//삭제
					else if (choice == 3) {
						if(id1.equals("admin")) {
							dao.managerdelete();
							System.out.println("관리자권한 삭제완료");
							break;
						}else {
							if (dao.delete()) {
								System.out.println("삭제되었습니다");
								break;
							} else {
								System.out.println("삭제권한이 없습니다");
							}
							
						}
					//돌아가기
					} else if (choice == 4) {
						break;
					}
					//좋아요
					else if (choice == 5) {
						dao.like();
						continue;
					}
				}
			} else if (menu == 3) {
				if (start > 4 || end > 5) {
					start = start - 5;
					end = end - 5;
					continue;
				} else {
					System.out.println("첫페이지입니다");
					continue;
				}

			} else if (menu == 4) {
				if (list.size() >= end) {
					start = end;
					end = end + 5;
					continue;
				} else {
					System.out.println("마지막페이지입니다");
					continue;
				}
			}

		}
	}
}
