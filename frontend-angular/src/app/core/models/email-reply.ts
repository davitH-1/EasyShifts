import { EmailAttachment } from './email-attachment';

export class EmailReply {
  id: string; // Unique ID for this specific record
  conversationId: string; // ID to group the whole "chain"
  parentId?: string; // ID of the message being replied to

  subject: string;
  bodyText: string;
  from: string;
  to: string;

  attachments?: EmailAttachment[];
  receivedAt: Date;

  // UI Helpers
  isRead: boolean;
  direction: 'incoming' | 'outgoing';

  constructor(data: Partial<EmailReply>) {
    this.id = data.id || '';
    this.conversationId = data.conversationId || '';
    this.parentId = data.parentId;
    this.subject = data.subject || '';
    this.bodyText = data.bodyText || '';
    this.from = data.from || '';
    this.to = data.to || '';
    this.attachments = data.attachments || [];
    this.receivedAt = data.receivedAt ? new Date(data.receivedAt) : new Date();
    this.isRead = !!data.isRead;
    this.direction = data.direction || 'incoming';
  }
}
