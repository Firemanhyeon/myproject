package com.yedam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class YdBrdDao {
	Connection conn;
	PreparedStatement psmt;
	PreparedStatement psmt2;
	ResultSet rs;
	String sql;
	String remid;
	int srcnum;

	// close메소드
	public void close() {
		try {
			if (conn != null) {
				conn.close();
			}
			if (psmt != null) {
				psmt.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 로그인메소드
	public boolean login(String id, String pw) {
		sql = "select * from id_pw where user_id = ? and user_pw = ?";
		conn = Dao.getConnect();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			psmt.setString(2, pw);

			rs = psmt.executeQuery();
			if (rs.next()) {
				remid = id;
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}

	public boolean newid(Ydbrd newid) {
		sql = "insert into id_pw values(?,?)";
		conn = Dao.getConnect();

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, newid.getId());
			psmt.setString(2, newid.getPw());

			int r = psmt.executeUpdate();
			if (r > 0) {
				return true;
			}
		} catch (SQLException e) {
//			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}

	// 댓글목록
	public List<Ydbrd> comment() {
		List<Ydbrd> list = new ArrayList<>();
		sql = "select user_commentno , user_comment , user_id , create_date from hh_comment where brd_no = ? order by create_date";
		conn = Dao.getConnect();

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, srcnum);
			rs = psmt.executeQuery();

			while (rs.next()) {
				Ydbrd brd = new Ydbrd();
				brd.setCommentnum(rs.getInt("user_commentno"));
				brd.setComment(rs.getString("user_comment"));
				brd.setId(rs.getString("user_id"));
				brd.setDate(rs.getDate("create_date"));
				list.add(brd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return list;
	}

	// 글목록
	public List<Ydbrd> list() {
		List<Ydbrd> list = new ArrayList<>();
		sql = "select brd_no, brd_title,create_date from hh_brd order by create_date";
		conn = Dao.getConnect();

		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();

			while (rs.next()) {
				Ydbrd brd = new Ydbrd();
				brd.setBrdnum(rs.getInt("brd_no"));
				brd.setTitle(rs.getString("brd_title"));
				brd.setDate(rs.getDate("create_date"));
				list.add(brd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return list;
	}

	// 글 등록 메소드
	public boolean add(Ydbrd add) {
		sql = "insert into hh_brd (brd_no , brd_title , brd_content,user_id)" + " values(board_seq.nextval,?,?,?) ";
		conn = Dao.getConnect();

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, add.getTitle());
			psmt.setString(2, add.getContent());
			psmt.setString(3, remid);

			int r = psmt.executeUpdate();
			if (r > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}

	// 댓글작성메소드
	public boolean commentmethod(Ydbrd comm) {
		sql = "insert into hh_comment (user_commentno, user_comment,"
				+ "user_id,brd_no) values(comm_seq.nextval,?,?,?)";
		conn = Dao.getConnect();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, comm.getComment());
			psmt.setString(2, remid);
			psmt.setInt(3, srcnum);

			int r = psmt.executeUpdate();
			if (r > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}

	// 매니저 삭제 메소드
	public boolean managerdelete() {
		sql = "delete from hh_brd where brd_no = ?  ";
		conn = Dao.getConnect();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, srcnum);

			int r = psmt.executeUpdate();

			if (r > 0) {

				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}

	// 글 삭제 메소드
	public boolean delete() {
		sql = "delete from hh_brd where brd_no = ? and user_id = ?";
		conn = Dao.getConnect();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, srcnum);
			psmt.setString(2, remid);
			int r = psmt.executeUpdate();

			if (r > 0) {

				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}

	// 관리자권한 수정 메소드
	public boolean managermodify(Ydbrd modify) {
		sql = "update hh_brd set brd_content =? " + "where brd_no = ?  ";
		conn = Dao.getConnect();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, modify.getContent());
			psmt.setInt(2, srcnum);

			int r = psmt.executeUpdate();

			if (r > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}

	// 수정메소드
	public boolean modify(Ydbrd modify) {
		sql = "update hh_brd set brd_content =? " + "where brd_no = ? and user_id = ?";
		conn = Dao.getConnect();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, modify.getContent());
			psmt.setInt(2, srcnum);
			psmt.setString(3, remid);

			int r = psmt.executeUpdate();

			if (r > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}

	// 상세보기메소드
	public Ydbrd deepsrc(int num) {
		sql = "select * from hh_brd where brd_no = ?";
		String count = "update hh_brd set click_cnt = click_cnt+1 where brd_no = ?";
		conn = Dao.getConnect();
		try {
			psmt = conn.prepareStatement(sql);
			psmt2 = conn.prepareStatement(count);

			psmt.setInt(1, num);
			psmt2.setInt(1, num);
			int rs2 = psmt2.executeUpdate();
			rs = psmt.executeQuery();

			while (rs.next()) {
				Ydbrd brd = new Ydbrd();
				brd.setBrdnum(rs.getInt("brd_no"));
				brd.setTitle(rs.getString("brd_title"));
				brd.setContent(rs.getString("brd_content"));
				brd.setDate(rs.getDate("create_date"));
				brd.setId(rs.getString("user_id"));
				brd.setCount(rs.getInt("click_cnt"));
				brd.setLike(rs.getInt("brd_like"));
				srcnum = num;
				return brd;

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return null;
	}

	public boolean like() {
		sql = "update hh_brd set brd_like = brd_like+1 where brd_no = ? ";
		conn = Dao.getConnect();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, srcnum);

			int r = psmt.executeUpdate();
			if (r > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}
}
