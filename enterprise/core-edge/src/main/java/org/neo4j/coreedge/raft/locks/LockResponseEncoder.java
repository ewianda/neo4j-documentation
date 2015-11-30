/*
 * Copyright (c) 2002-2015 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.coreedge.raft.locks;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import org.neo4j.coreedge.raft.replication.StringMarshal;

public class LockResponseEncoder extends MessageToMessageEncoder<LockMessage.Response>
{
    @Override
    protected void encode( ChannelHandlerContext ctx, LockMessage.Response msg, List<Object> out ) throws Exception
    {
        ByteBuf buffer = ctx.alloc().buffer();
        LockMessageMarshall.serialize( buffer, msg.request );

        buffer.writeInt( msg.result.getStatus().ordinal() );
        StringMarshal.serialize( buffer, msg.result.getMessage() );

        out.add( buffer );
    }
}