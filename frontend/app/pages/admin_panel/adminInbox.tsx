import { useState } from 'react';
import "./adminInbox.css";

export default function AdminInboxContent() {
  const mails = [
    { title: "Mediterranean Cuisine", type: "Update course", status: "Pending", sender: "john_doe", time: "10:15 AM" },
    { title: "Permission to become instructor: Ante Antic", type: "Promote instructor", status: "Approved", sender: "ante123", time: "09:40 AM" },
    { title: "Harassment in course review", type: "Report", status: "Pending", sender: "ivan_horvat", time: "Yesterday" },
    { title: "American cuisine", type: "Update course", status: "Declined", sender: "alex_brown", time: "2 days ago" },
  ];

  const statusColors: Record<string, string> = {
    Pending: "#f0ed49d7",
    Approved: "#10B981",
    Declined: "#EF4444",
  };

  return (
    <section id="content">
      <div className="header-wrapper">
        <header className="admin-header">
          <h1>Admin Inbox</h1>
      
        </header>
      </div>

      <div className="inbox-list">
          <div className="inbox-specifications-header">
            <div className="inbox-title-header">Title</div>
            <div className="inbox-type-header">Type</div>
            <div className="inbox-sender-header">Sender</div>
            <div className="inbox-time-header">Time</div>
            <div className="inbox-status-header">Status</div>
            </div>
        {mails.map((mail, index) => (
          <div key={index} className="inbox-item">
            <div className="inbox-title">{mail.title}</div>
            <div className="inbox-type">{mail.type}</div>
            <div className="inbox-sender">{mail.sender}</div>
            <div className="inbox-time">{mail.time}</div>
            <div
              className="inbox-status"
              style={{ backgroundColor: statusColors[mail.status] }}
            >
              {mail.status}
            </div>
          </div>
        ))}
      </div>
    </section>
  );
}
