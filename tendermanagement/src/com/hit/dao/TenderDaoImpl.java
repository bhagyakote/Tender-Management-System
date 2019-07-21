package com.hit.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hit.beans.TenderBean;
import com.hit.utility.DBUtil;

public class TenderDaoImpl implements TenderDao{

	@Override
	public List<TenderBean> getTenderDetails(String tid) {
		List<TenderBean> tenderList = new ArrayList<TenderBean>();
		
		Connection con = DBUtil.provideConnection();
		
		PreparedStatement ps = null;
		PreparedStatement pst = null;
		try {
			
			ps = con.prepareStatement("select * from tender where tid=?");
			
			ps.setString(1, tid);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()){
				TenderBean tender = new TenderBean();
			String id =  	rs.getString(1);
			String name=	rs.getString(2);
			String type=	rs.getString(3);
			int price   =	    rs.getInt(4);
			String desc=	rs.getString(5);
			String deadline=rs.getString(6);
			String location=rs.getString(7);
			
			tender=new TenderBean(id, name, type, price, desc, deadline, location);
			tenderList.add(tender);
			}
			else{
				pst = con.prepareStatement("select * from tender where tname like '%"+tid+"%'");
				
				ResultSet rss = pst.executeQuery();
				
				while(rss.next()){
					TenderBean tender = new TenderBean();
					String id =  	rss.getString(1);
					String name=	rss.getString(2);
					String type=	rss.getString(3);
					int price   =	rss.getInt(4);
					String desc=	rss.getString(5);
					String deadline=rss.getString(6);
					String location=rss.getString(7);
					tender=new TenderBean(id, name, type, price, desc, deadline, location);
					tenderList.add(tender);
				}
				
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			
			DBUtil.closeConnection(ps);
			
			DBUtil.closeConnection(pst);
			
			DBUtil.closeConnection(con);
			
		}
		
		
		
		return tenderList;
	}

	@Override
	public List<TenderBean> getAllTenders() {
		List<TenderBean> tenderList  = new ArrayList<TenderBean>();
		
		Connection con = DBUtil.provideConnection();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = con.prepareStatement("select * from tender");
			
			rs=ps.executeQuery();
			while(rs.next())
			{
				TenderBean tender=new TenderBean();
				
				tender.setId(rs.getString("tid"));
				tender.setName(rs.getString("tname"));
				tender.setType(rs.getString("ttype"));
				tender.setPrice(rs.getInt("tprice"));
				tender.setDesc(rs.getString("tdesc"));
				java.util.Date udate = new java.util.Date(rs.getDate(6).getTime());
				tender.setDeadline(udate);
				tender.setLocation(rs.getString("tloc"));
				tenderList.add(tender);
			}
			
		} catch (SQLException e) {

			e.printStackTrace();
		}
		finally{
			
			DBUtil.closeConnection(ps);
			
			DBUtil.closeConnection(rs);
			
			DBUtil.closeConnection(con);
			
		}
		
		
		return tenderList;
	}

	@Override
	public String createTender(TenderBean tender) {
		
		String status = "Tender Addition Failed!";
		
		Connection conn=DBUtil.provideConnection();
		
		PreparedStatement pst = null;
		
		try {
			pst= conn.prepareStatement("insert into tender values(?,?,?,?,?,?,?)");
			pst.setString(1, tender.getId());
			pst.setString(2, tender.getName());
			pst.setString(3, tender.getType());
			pst.setInt(4, tender.getPrice());
			pst.setString(5, tender.getDesc());
			
			Date deadline = tender.getDeadline();
			java.sql.Date sDeadline = new java.sql.Date(deadline.getTime());
			pst.setDate(6, sDeadline);
			
			pst.setString(7, tender.getLocation());
			
			int x=pst.executeUpdate();
			if(x>0)
				status = "New Tender Inserted<br> Your Tender id: "+tender.getId();
		
		} catch (SQLException e) {
			
			status="Error : "+e.getMessage();
			
			e.printStackTrace();
		}
		finally{
		
			DBUtil.closeConnection(pst);
			
			DBUtil.closeConnection(conn);
			
		}
		
		return status;
	}

	@Override
	public boolean removeTender(String tid) {
boolean flag=false;
		
		Connection con = DBUtil.provideConnection();
		
		PreparedStatement ps = null;
		try {
			
			ps = con.prepareStatement("delete from tender where tid=?");
			
			ps.setString(1, tid);
			
			int x= ps.executeUpdate();
			
			if(x>0){
			
			
			flag=true;
			
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			
			DBUtil.closeConnection(ps);
			
			DBUtil.closeConnection(con);
			
		}
				
		return flag;
	}

	@Override
	public String updateTender(TenderBean tender) {
		String status = "Tender Updation Failed!";
		
		Connection con = DBUtil.provideConnection();
		
		PreparedStatement pst = null;
		
		try {
			pst = con.prepareStatement("UPDATE tender SET tname=?,ttype=?,tprice=?,tdesc=?,tdesc=?,tloc=? where tid=?");
			
			pst.setString(1, tender.getName());
			pst.setString(2, tender.getType());
			pst.setInt(3, tender.getPrice());
			pst.setString(4, tender.getDesc());
			
			Date deadline = tender.getDeadline();
			java.sql.Date sDeadline = new java.sql.Date(deadline.getTime());
			pst.setDate(5, sDeadline);
			
			pst.setString(6, tender.getLocation());
			pst.setString(7, tender.getId());
			int x=pst.executeUpdate();
			if(x>0)
				status="TENDER DETAILS UPDATED SUCCSESFULLY";
			
		} catch (SQLException e) {
			status = "Error: "+e.getMessage();
			e.printStackTrace();
		}
		finally{
			
			DBUtil.closeConnection(pst);
			
			DBUtil.closeConnection(con);
			
		}
		
		return status;
	}

	@Override
	public boolean acceptBid(String applicationId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rejectBid(String applicationId) {
		// TODO Auto-generated method stub
		return false;
	}

}